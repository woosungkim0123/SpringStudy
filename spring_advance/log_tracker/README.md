# 로그 추적기

HTTP 요청 단위로 추적 가능한 로그 추적기를 만드는 것이 목표입니다.

## 버전별 변화

### V1

- 로그 트래킹 기능을 사용하여 로그를 남길 수 있도록 하였습니다.

**단점**

- 예외처리시 받을 상태값 때문에 스코프를 넓혀서 처리해야합니다.
- 컨트롤러, 서비스, 리포지토리 각 단계별로 트랜잭션 ID, 레벨을 동기화 처리가 필요합니다.
- 중복된 부분이 많아 코드가 너무 복잡합니다.

### V2 (파라미터 전달)

- 파라미터로 트랜잭션 ID와 레벨을 동기화 처리하였습니다.

**단점**

- 비지니스 로직에 관련된 정보가 아닌 단순히 로그를 위한 코드인데 파라미터로 넘겨야합니다.
- 파라미터를 사용하다보니 기존 코드에 추가시 모든 파라미터를 수정해야합니다.
- 로그 처음 시작할 때 `begin`을 호출하고 `beginSync`를 호출해야합니다.
- 다른곳에서 서비스를 처음 호출하는 상황에는 traceId가 없습니다.

### V3 (필드 저장)

- 로그 추적기의 필드에 트랜잭션ID와 레벨을 저장하여 동기화 처리를 하였습니다.
- 필드에 저장하여 사용하므로 파라미터로 넘기지 않아도 됩니다.

**단점**

- 로그 추적기를 빈으로 등록하여(싱글톤) 사용하므로 동시성 문제가 발생합니다.

> - 동시성 문제는 지역변수에서는 발생하지 않습니다. 지역변수는 쓰레드마다 각각 다른 메모리 영역이 할당됩니다.  
> - 동시성 문제가 발생하는 곳은 같은 인스턴스 필드(주로 싱글톤에서 발생) 또는 static 같은 공용 필드에 접근할 때 발생합니다.
> - 동시성 문제는 값을 읽기만 하면 발생하지 않지만 값을 변경하기 때문에 발생합니다.

```java
public class ConcurrencyExample {
    private static int sharedCounter = 0; // 공용 필드

    public void incrementSharedCounter() {
        sharedCounter++; // 여러 쓰레드가 동시에 접근하면 동시성 문제 발생 가능
    }

    public void methodWithLocalVariable() {
        int localCounter = 0; // 지역변수
        localCounter++; // 각 쓰레드가 독립적으로 사용, 동시성 문제 없음
    }
}
```

### V4 (쓰레드 로컬 사용)

- 쓰레드 로컬을 사용하여 동시성 문제를 해결하였습니다.

**단점**

- 핵심 기능과 부가 기능(로그 추적기) 코드가 섞여 있습니다.
- 부가 기능 코드가 핵심 기능 코드보다 더 많아 코드가 복잡합니다.

### 쓰레드 로컬(ThreadLocal)

- 쓰레드 로컬은 해당 쓰레드만 접근할 수 있는 특별한 저장소를 말합니다.
- 쓰레드 로컬을 사용하면 동일한 쓰레드에서만 데이터를 공유할 수 있습니다.
- get() 메서드로 데이터를 읽고 set() 메서드로 데이터를 저장, remove() 메서드로 데이터를 삭제할 수 있습니다.
- 쓰레드 로컬을 모두 사용한 후에는 반드시 remove() 메서드로 데이터를 삭제해야합니다.

```java
public class ThreadLocalService {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public String logic(String name) {
        threadLocal.set(name); // 쓰레드 로컬에 저장
        // logic ...
        return threadLocal.get(); // 쓰레드 로컬에서 읽기
    }
}
```

**주의 사항**

웹 애플리케이션 서버(WAS)는 효율적인 자원 관리를 위해 쓰레드 풀을 사용합니다.

> **쓰레드 풀(Thread Pool)**
>
> 쓰레드 생성과 제거에 따른 비용을 줄이기 위해서 사용자의 요청이 들어오면 WAS는 쓰레드 풀에서 쓰레드를 할당받아 작업을 처리하고, 작업이 완료되면 해당 쓰레드를 다시 쓰레드 풀에 반환합니다.

ThreadLocal을 사용할 때는 쓰레드의 작업이 끝난 후에 반드시 ThreadLocal에 저장된 데이터를 제거해야 합니다.

만약 ThreadLocal에 저장된 데이터를 제거하지 않고 쓰레드를 쓰레드 풀에 반환하게 되면, 그 쓰레드가 다음 번에 다른 작업을 할당받았을 때 ThreadLocal에 이전 작업에서 사용된 데이터가 남아있게 되고 문제가 발생할 수 있습니다.

예를 들어, 사용자 A의 요청을 처리하는 동안 사용자 A의 개인 정보를 ThreadLocal에 저장했다고 가정해봅시다.
작업 완료 후 이 데이터를 제거하지 않고 쓰레드를 쓰레드 풀에 반환하면, 이 쓰레드가 다음 요청을 처리하게 될 때 ThreadLocal에 남아있는 사용자 A의 정보가 사용자 B에게 노출될 가능성이 있습니다.

이러한 문제를 방지하기 위해, ThreadLocal을 사용한 후에는 반드시 ThreadLocal.remove() 메서드를 호출하여 저장된 데이터를 제거해야 합니다.

### V5 (템플릿 메서드 패턴)

- 템플릿 메서드 패턴을 사용하여 코드를 정리하였습니다.
- 코드 수정시 변경이 여러곳이 아닌 한곳에서만 수정하면 됩니다. (단일 책임 원칙)

**단점**

- 자식 클래스에서 부모 클래스의 기능을 사용하지 않을 경우에도 부모 클래스의 기능을 상속받아야 합니다. (상속의 단점)
- 별도의 클래스나 익명 내부 클래스를 만들어야 하는 부분도 복잡합니다.

### 템플릿 메서드 패턴

코드에서 동일한 패턴으로 중복이 계속 발생하는 데 이때 템플릿 메서드 패턴을 사용하여 해결 할 수 있습니다.

변하는 부분과 변하지 않는 부분을 분리하는 것으로 구조를 정의하고 일부 단계의 구현을 서브클래스로 연기하는 것입니다.

```java
public class TemplateMethodTest {

    private void logic1() {
        long startTime = System.currentTimeMillis();
        
        log.info("비즈니스 로직1 실행"); // 이 부분 뺴고 나머지는 변하지 않는 부분
        
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
```

**템플릿 메서드 패턴 적용시**

```java
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();
        
        call(); // 상속
        
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
    
    protected abstract void call();
}

// 익명 내부 클래스를 사용해도 됩니다.
public class SubClassLogic1 extends AbstractTemplate {
    @Override
    protected void call() {
        log.info("비즈니스 로직1 실행");
    }
}
```

### V6 (템플릿 콜백 패턴 사용)

- 템플릿 콜백 패턴을 사용하여 코드를 정리하였습니다.

### 전략 패턴

템플릿 콜백 패턴 전에 전략 패턴에 대해 알아보겠습니다.
 
템플릿 메서드 패턴과 유사하지만 변하지 않는 부분을 Context에 두고 변하는 부분을 Strategy 인터페이스로 만들어 이를 구현하도록 해서 문제를 해결합니다.

**필드에 전략을 주입하는 방식**

- Context와 Strategy를 한번 조립하고 나면 Context를 실행만 하면 되는 것이 장점입니다.
- 조립한 이후에 전략을 변경하기 어렵다는 단점이 있습니다. 전략을 변경하면 동시성 이슈를 고려해야합니다.

**인자로 전략을 주입하는 방식**

- 메서드의 인자로 전략을 주입받음으로써 실시간으로 전략을 변경할 수 있습니다.
- 호출시마다 전략을 주입해야 하는 것이 단점입니다.

```java
public class Context {
    // 필드로 선언해서 생성자로 받는 방법도 있지만 메서드로 받는 것이 유연하게 변경가능
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();

        strategy.call(); // 위임

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}

public interface Strategy {
    void call();
}

public class StrategyLogic1 implements Strategy{
    @Override
    public void call() {
        log.info("비지니스1 실행");
    }
}

public class StrategyLogic2 implements Strategy{
    @Override
    public void call() {
        log.info("비지니스2 실행");
    }
}

public class ContextTest {
    @Test
    void strategy() {
        Context context = new Context();
        context.execute(new StrategyLogic1()); // 구현이 아닌 람다를 사용해서 처리 가능합니다. () -> log.info("비지니스1 실행");
        context.execute(new StrategyLogic2());
    }
}
```

### 템플릿 콜백 패턴

전략 패턴에서 변하는 부분을 파라미터로 넘겨 실행시켰는 데 이때 다른 코드의 인수로 넘겨주어 실행 가능한 코드를 콜백(callback)이라고 합니다.

```java
public class ContextTest {
    @Test
    void strategy() {
        Context context = new Context();
        context.execute(() -> log.info("비지니스1 실행")); // 여기서 람다 함수 부분이 콜백입니다.
    }
}
```

스프링에서는 메서드로 전략을 넘기는 방식의 전략 패턴을 템플릿 콜백 패턴이라고 부릅니다. 

전략 패턴에서 Context가 템플릿 역할을 하고 Strategy 부분이 콜백으로 넘어 오게됩니다.

- `Context` -> `Template`
- `Strategy` -> `Callback`

템플릿 콜백 패턴은 GOF 패턴이 아니고 스프링 안에서 부르는 명칭이고 전략 패턴에서 템플릿과 콜백 부분이 강조된 패턴입니다.

스프링은 JdbcTemplate, RestTemplate, RedisTemplate 등 XxxTemplate 같은 다양한 템플릿 콜백 패턴을 사용한 예들이 많습니다. 

```java
public class TimeLogTemplate {

    public void execute(Callback callBack) {
        long startTime = System.currentTimeMillis();

        callBack.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}

public interface Callback {
    void call();
}

public class TemplateCallbackTest {
    @Test
    void callback() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비지니스 로직1 실행"));
        template.execute(() -> log.info("비지니스 로직2 실행"));
    }
}
```

## 한계

여러가지 방식을 사용해서 로그 추적기를 만들어 보았지만 로그 추적기를 적용하려면 원본 코드를 수정해야 한다는 한계가 존재합니다. 

수백개의 클래스에 적용시 많은 코드를 수정해야 합니다. (원본 코드에 손을 대지 않고 로그 추적기를 적용할 수 있는 방법은 없을까요?)
