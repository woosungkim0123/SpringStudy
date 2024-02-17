# AOP

## AOP 이론

[1. AOP 기초](https://github.com/woosungkim0123/spring-jpa-deep-dive/tree/master/spring_advance/aop/notion/1_aop_basic)


## 버전별 변경 사항

### v1 - AOP 적용

- AOP를 적용하여 Service, Repository에 로그를 남기는 기능을 추가
- `AspectV1` 참조

### v2 - 포인트컷 분리

- 포인트컷을 분리하여 여러 어드바이스에서 재사용할 수 있도록 변경
- `AspectV2` 참조

### v3 - 여러 어드바이스 추가

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

