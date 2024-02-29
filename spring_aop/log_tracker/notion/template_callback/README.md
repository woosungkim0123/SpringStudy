# 템플릿 콜백 패턴

## 전략 패턴

템플릿 콜백 패턴 전에 전략 패턴에 대해 알아보겠습니다.

템플릿 메서드 패턴과 유사하지만 변하지 않는 부분을 Context에 두고 변하는 부분을 Strategy 인터페이스로 만들어 이를 구현하도록 해서 문제를 해결합니다.

### 필드에 전략을 주입하는 방식

- Context와 Strategy를 한번 조립하고 나면 Context를 실행만 하면 되는 것이 장점입니다.
- 조립한 이후에 전략을 변경하기 어렵다는 단점이 있습니다. 전략을 변경하면 동시성 이슈를 고려해야합니다.

### 인자로 전략을 주입하는 방식

- 메서드의 인자로 전략을 주입받음으로써 실시간으로 전략을 변경할 수 있습니다.
- 호출시마다 전략을 주입해야 하는 것이 단점입니다.

### 예시 코드

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

<br>

## 스프링의 템플릿 콜백 패턴

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

### 예시 코드

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