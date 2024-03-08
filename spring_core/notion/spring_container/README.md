# 스프링 컨테이너

## 스프링 컨테이너 생성

스프링 컨테이너(`ApplicationContext`)는 인터페이스로 XML 기반으로 읽는 `ClassPathXmlApplicationContext`와 어노테이션 기반으로 읽는 `AnnotationConfigApplicationContext`가 있습니다.

```java
public class ContainerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
```

스프링 컨테이너를 생성할 때 설정 정보를 넘겨줌으로써 이를 사용해 스프링 빈을 생성하게 됩니다. (빈 이름은 기본적으로 메서드 이름을 사용합니다.)

빈이 생성되면 설정 정보를 참고해서 의존 관계를 주입합니다.

<br>

## 스프링 컨테이너 구성

BeanFactory는 최상위 인터페이스로 스프링 빈을 관리하고 조회하는 역할을 담당합니다.

ApplicationContext는 BeanFactory의 모든 기능과 부가 기능을 제공합니다.

BeanFactory와 ApplicationContext를 스프링 컨테이너라고 합니다.

### ApplicationContext가 제공하는 부가 기능

- 메시지 소스를 활용한 국제화 기능 (한국에서 들어오면 한국어, 영어권에서 들어오면 영어로 출력)
- 환경변수 (로컬, 개발, 운영 등을 구분해서 처리)
- 애플리케이션 이벤트 (이벤트를 발행하고 구독하는 모델을 편리하게 지원)
- 편리한 리소스 조회 (파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회)

### 스프링 빈 설정 메타 정보 (BeanDefinition)

스프링은 다양한 설정 형식을 지원하는데 이것은 스프링 빈 설정 메타 정보를 추상화해서 `BeanDefinition`으로 정의했기 때문입니다.

XML을 읽어서 `BeanDefinition`을 만들 수 있고, 자바 코드를 읽어서 `BeanDefinition`을 만들 수 있습니다.

스프링 컨테이너는 자바 코드인지, XML인지 상관없이 `BeanDefinition`만 알고 있으면 됩니다.

`BeanDefinition`을 빈 설정 메타 정보라고 합니다.

스프링은 빈 설정 메타 정보를 `BeanDefinition`으로 추상화해서 정의합니다. (`@Bean`당 각각 하나씩 메타 정보가 생성됩니다.)

스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성합니다.
