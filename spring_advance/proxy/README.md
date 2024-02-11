# 프록시

## 요구 사항

- 원본 코드를 전혀 수정하지 않고 로그 추적기 기능을 적용해보겠습니다.
- 특정 메서드는 로그를 출력하지 않도록 해야합니다.
- 인터페이스가 있는 구현 클래스, 인터페이스가 없는 구체 클래스, 컴포넌트 스캔 대상 등 다양한 케이스에 다 적용할 수 있어야합니다.

이 문제를 해결하려면 프록시(Proxy) 개념을 먼저 이해해야합니다. 프록시에 대한 자세한 이해는 [프록시 개념](#프록시-개념)를 참고해주세요.

## 버전별 프록시 적용

### V1

**기본 클래스 의존 관계**

![v1 클래스 의존관계](image/v1_class_relation.png)

**런타임 객체 의존 관게**

![v1 런타임 객체 의존관계](image/v1_runtime_realation.png)


## 프록시 개념

클라이언트(요청하는 객체)가 직접 서버(요청을 처리하는 객체)에 요청하는 것이 아닌 대리자를 통해 간접적으로 요청할 때 대리자를 프록시(Proxy)라고 합니다.

![프록시 개념](image/proxy_notion.png)

## 프록시 장점

직접 호출이 아닌 대리자를 통해 간접 호출시 대리자가 중간에서 여러가지 일을 할 수 있습니다.

1. 데이터 요청시 이미 캐시에 데이터가 있는 경우 서버에 요청하지 않고 캐시에 있는 데이터를 반환할 수 있습니다.(접근 제어, 캐싱)

2. 데이터 요청시 클라이언트가 기대한 것 외에 추가적인 기능을 제공할 수 있습니다.(부가 기능 추가)

3. 대리자가 또 다른 대리자를 부를 수도 있습니다. 클라이언트는 대리자를 통해 요청했는데 그 이후는 모릅니다. (프록시 체인)

![프록시 체인](image/proxy_chain.png)

## 대체 가능

클라이언트는 서버에게 요청한 것인지 프록시에게 요청한 것인지 모릅니다. 

서버와 프록시는 같은 인터페이스를 사용해야하며 DI를 통해 클라이언트 코드 변경없이 주입할 수 있습니다.

![대체가능](image/proxy_di.png)

## 프록시 주요 기능

1. 접근 제어
    - 권한에 따른 접근
    - 캐싱
    - 지연 로딩


2. 부가 기능 추가
    - 요청 값이나 응답 값을 중간에 변형
    - 추가 로그 출력

## 프록시 사용 방식에 따른 분류

둘다 프록시를 사용하는 방식이지만 의도에 따라 프록시 패턴과 데코레이터 패턴으로 구분합니다.

- 프록시 패턴: 접근 제어가 목적
- 데코레이터 패턴: 부가 기능 추가가 목적

## 프록시 패턴

- 코드를 변경없이 프록시를 도입해서 접근 제어를 하는 패턴입니다.
- 프록시 패턴은 프록시를 사용하는 여러 패턴 중 하나일 뿐 입니다.
- 클라이언트는 프록시 객체가 주입되었는지 실제 객체가 주입되었는지 모릅니다.
- 다른 개체에 대한 접근을 제어하기 위해 대리자를 제공합니다.

![프록시 패턴](image/proxy_pattern.png)

### 프록시 패턴 적용 전

- 클라이언트가 서버(RealSubject)에 직접 요청합니다.

```java
public interface Subject {
    String operation();
}

// 실제 객체
@Slf4j
public class RealSubject implements Subject {

   @Override
   public String operation() {
      log.info("실제 객체 호출");
      sleep(1000); // 데이터 조회에 1초 걸림을 가정
      return "data";
   }

   private void sleep(int millis) {
      try {
         Thread.sleep(millis);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}

// 클라이언트
public class ProxyPatternClient {

   private Subject subject;

   public ProxyPatternClient(Subject subject) {
      this.subject = subject;
   }

   public void execute() {
      subject.operation();
   }
}

public class ProxyPatternTest {
    /**
     * operation 반환 값이 변하지 않는 데이터인데 어딘가 보관해두고 사용하면 성능상 좋습니다. (캐시)
     * client -> realSubject
     */
    @Test
    void noProxyTest() {
        Subject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        client.execute(); // 1초
        client.execute(); // 1초
        client.execute(); // 1초
    }
}
```

**프록시 패턴 적용 후**

- 코드를 변경하지 않고 프록시 객체를 주입하여 접근 제어 중 하나인 캐싱을 적용합니다.

```java
@Slf4j
public class CacheProxy implements Subject {

    private Subject target; // 실제 객체에 접근할 수 있어야 합니다.
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if(cacheValue == null) {
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}

public class ProxyPatternTest {
    /**
     * 코드를 전혀 수정하지 않고 프록시 객체를 통해서 캐시를 적용할 수 있습니다.
     * client -> proxy -> realSubject
     */
    @Test
    void proxyTest() {
        Subject subject = new RealSubject(); // 실제 객체
        Subject proxy = new CacheProxy(subject); // 프록시
        ProxyPatternClient client = new ProxyPatternClient(proxy);

        client.execute(); // 캐시가 없어서 realSubject.operation() 호출 -> 캐시 저장 (1초)
        client.execute(); // 캐시가 있어서 realSubject.operation() 호출하지 않고 캐시 반환 (0초)
        client.execute(); // 캐시가 있어서 realSubject.operation() 호출하지 않고 캐시 반환 (0초)
    }
}
```

## 데코레이터 패턴

- 코드를 변경하지 않고 프록시를 도입해서 부가 기능을 추가하는 패턴입니다.
- 실제 객체가 있고 이를 데코레이터로 감싸서 부가 기능을 추가합니다.
- 객체에 추가 책임(기능)을 동적으로 추가하고 기능 확장을 위한 유연한 대안을 제공합니다.

![데코레이터 패턴](image/decorate.png)

### 사용 목적

기존 코드를 변경하지 않고도 객체의 기능을 확장할 수 있어, 기능 추가와 관련된 복잡성과 클래스 수의 증가 문제를 해결할 수 있습니다.

### 코드

**기본 기능**

```java
public interface Component {
    String operation();
}

@Slf4j
public class RealComponent implements Component {
   @Override
   public String operation() {
      log.info("RealComponent 실행");
      return "data";
   }
}

@Slf4j
public class DecoratorClient {

   private Component component;

   public DecoratorClient(Component component) {
      this.component = component;
   }

   public String execute() {
      log.info("DecoratorClient 실행");
      return component.operation();
   }
}

public class DecoratorPatternTest {
    @Test
    public void noDecoratorPatternTest() {
        Component component = new RealComponent();
        DecoratorClient client = new DecoratorClient(component);

        client.execute();
    }
}
```

**코드 변경없이 기능 추가**

```java
public abstract class Decorator implements Component {
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }
}

public class MessageDecorator extends Decorator {

   public MessageDecorator(Component component) {
      super(component);
   }

   @Override
   public String operation() {
      log.info("MessageDecorator 실행");

      String result = component.operation();
      String decoResult = "****" + result + "****";

      log.info("적용전 : {}, 적용후 : {}", result, decoResult);

      return decoResult;
   }
}

@Slf4j
public class TimeDecorator extends Decorator {

   public MessageDecorator(Component component) {
      super(component);
   }

   @Override
   public String operation() {
      log.info("TimeDecorator 실행");
      long start = System.currentTimeMillis();

      String result = component.operation();

      long end = System.currentTimeMillis();
      log.info("실행시간 : {}", end - start);

      return result;
   }
}

public class DecoratorPatternTest {
    @Test
    public void decoratorPatternTest2() {
        Component component = new RealComponent();
        Component messageDecorator = new MessageDecorator(component);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorClient client = new DecoratorClient(timeDecorator); // Client 코드를 전혀 수정하지 않음
       
        String result = client.execute();
        
        System.out.println(result);
    }
}
```