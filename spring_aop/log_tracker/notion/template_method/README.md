# 템플릿 메서드 패턴

## 개념

동일한 패턴의 중복이 지속적으로 발생하는 문제를 템플릿 메서드 패턴을 사용하여 해결 할 수 있습니다.

변하는 부분과 변하지 않는 부분을 분리하는 것으로 구조를 정의하고 일부 단계의 구현을 서브클래스로 연기하는 것입니다.

## 코드 예시

### 기본

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

### 템플릿 메서드 패턴 적용

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