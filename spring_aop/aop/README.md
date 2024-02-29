# AOP

## AOP 이론

1. [AOP 기초](https://github.com/woosungkim0123/spring-jpa-deep-dive/tree/master/spring_aop/aop/notion/1_aop_basic)
   - AOP의 기본 개념과 용어
   - AOP 적용 방식
2. [어드바이스 종류](https://github.com/woosungkim0123/spring-jpa-deep-dive/tree/master/spring_aop/aop/notion/2_advice_type)  
   - @Around, @Before, @AfterReturning, @AfterThrowing, @After
3. [포인트컷 지시자](https://github.com/woosungkim0123/spring-jpa-deep-dive/tree/master/spring_aop/aop/notion/3_pointcut)

<br>

## 변경 사항

### AOP 적용

- AOP를 적용하여 Service, Repository에 로그를 남기는 기능을 추가
- `AspectV1` 참조

### 포인트컷 분리

- 포인트컷을 분리하여 여러 어드바이스에서 재사용할 수 있도록 변경
- `AspectV2` 참조

### 여러 어드바이스 추가

- 타입 이름 패턴이 *Service를 대상으로 하는 포인트컷 추가 (인터페이스, 클래스 모두 적용되어서 타입 이름 패턴이라 명칭)
- `OrderService`에는 `doLog`와 `doTransaction` 어드바이스 적용
- `OrderRepository`에는 `doLog`만 적용
- 포인트컷은 && (AND), || (OR), ! (NOT) 3가지 조합이 가능합니다.
  ```java
  @Around("allOrder() && allService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        // ...
  }
  ```
- `AspectV3` 참조

### 포인트컷 참조로 변경

- 포인트컷을 별도의 외부 클래스에 모아두고 호출하도록 변경(접근 제어자를 public으로 변경)
- 포인트컷을 조합하여 새로운 포인트컷을 추가
- 패키지명을 포함한 클래스 이름과 포인트컷 시그니처를 모두 지정하여 포인트컷을 참조할 수 있습니다.
- `AspectV4`, `Pointcut` 참조

### 어드바이스 순서 변경

- 어드바이스는 기본적으로 순서를 보장하지 않습니다.
- 순서를 지정하기 위해서는 @Aspect 적용 단위로 @Order를 적용해야합니다. @Aspect는 클래스 단위에 적용됩니다.
- 애스펙트를 별도의 클래스 파일로 분리하거나 내부 클래스로 분리하여 순서를 지정할 수 있습니다.
- `AspectV5` 참조

### 로그 출력 & 재시도 AOP 추가

- @Trace가 적용된 메소드에 로그 출력
- @Retry가 적용된 메소드에 재시도 로직 추가
- `exam` 패키지, `ExamTest` 참조