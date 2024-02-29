# 리플렉션 (reflection)

## 개념

구체적인 클래스 타입을 알지 못하더라도 그 클래스의 메서드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API를 말합니다.

컴파일 시간이 아닌 실행 시간에 동적으로 특정 클래스의 정보를 추출할 수 있습니다.

<br>

## 사용 목적

- 런타임 시점에서 어떤 클래스를 실행 해야할지 가져와 실행해야하는 경우 필요합니다.
- 프레임워크나 IDE에서 이런 동적 바인딩을 이용한 기능을 제공합니다.
- IntelliJ의 자동완성 기능, 스프링 어노테이션 등이 리플렉션을 사용합니다.

<br>

## 리플렉션을 사용해서 가져올 수 있는 정보

- Class
- Constructor
- Method
- Field

<br>

## 코드 예시

### 리플렉션 사용 전

```java
public class ReflectionTest {

    @Test
    void noReflection() {
        Hello target = new Hello();
        
        // 공통 로직1시작
        System.out.println("start");
        String result = target.callA(); // 호출하는 메서드만 다르고 다 똑같음
        System.out.println("result = " + result);
        // 공통 로직1끝
       
        // 공통 로직2시작
        System.out.println("start");
        String result2 = target.callB(); // 호출하는 메서드만 다르고 다 똑같음
        System.out.println("result2 = " + result2);
        // 공통 로직2끝
    }
}
```

### 리플렉션 사용 후

```java
public class ReflectionTest {
   @Test
   void reflection1() throws Exception {
      Class classHello = Class.forName("hello.proxy.dynamic.ReflectionTest$Hello"); // 클래스 정보
      Hello target = new Hello();
      
      Method methodCallA = classHello.getMethod("callA"); // callA 메서드 정보
      dynamicCall(methodCallA, target); // 획득한 메서드 메타정보로 실제 인스턴스의 메서드를 호출한다
      
      Method methodCallB = classHello.getMethod("callB"); // callB 메서드 정보
      dynamicCall(methodCallB, target); // 획득한 메서드 메타정보로 실제 인스턴스의 메서드를 호출한다
   }
   
    private void dynamicCall(Method method, Object target) throws Exception {
        System.out.println("start");

        Object result = method.invoke(target);

        System.out.println("result = " + result);
    }
}
```

<br>

## 주의 사항

- 리플렉션은 가급적이면 안써야합니다.
- 리플렉션을 사용하면 클래스와 메타정보를 사용해서 동적으로 유연하게 만들 수 있으나 런타임에 동작하기 때문에 컴파일 시점에 오류를 잡을 수 없습니다.
- 리플렉션은 프레임워크 개발이나 또는 매우 일반적인 공통 처리가 필요할 때 부분적으로 주의해서 사용해야 한다.