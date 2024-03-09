# 스프링 빈

스프링 빈의 등록 방식은 싱글톤이 기본이지만 싱글톤 방식이 아닌 빈 등록 방식도 지원합니다.

## @Configuration과 바이트 조작

`@Configuration`은 싱글톤을 위해 사용되는 어노테이션입니다.

```java
@Configuration
public class AppConfig {
    @Bean 
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    
    @Bean 
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(), 
                discountPolicy());
    }
    
    @Bean 
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

`AppConfig`를 확인해보면 각기 다른 `MemoryMemberRepository`가 생성될 것 같은데 어떻게 싱글톤을 유지하는 걸까?

AnnotationConfigApplicationContext에 파라미터로 넘긴 값은 스프링 빈으로 등록되는데 `AppConfig`도 스프링 빈으로 등록되어 있습니다.

`bean.getClass()`를 통해 확인해보면 내가 만든 클래스가 아니라 스프링이 CGLIB(바이코드 조작 라이브러리)를 사용해서 AppConfig를 상속받은 임의의 클래스를 만들고 그걸 스프링 빈으로 등록한 것입니다.

그 임의의 다른 클래스가 싱글톤을 보장하는 것입니다.

```java
// 예상 코드
public class AppConfig$$EnhancerBySpringCGLIB$$f3f3e3d extends AppConfig {
    
    @Override
    public MemberRepository memberRepository() {
        if (isExistMemberRepository) { // 스프링 컨테이너 등록되어 있으면
            return SpringContainer.getBean(MemberRepository.class);
        } else { // 스프링 컨테이너에 없으면
            MemberRepository memberRepository = new MemoryMemberRepository();
            SpringContainer.registerBean(MemberRepository.class, memberRepository);
            return memberRepository;
        }
    }
}
```

만약 `@Configuration`을 사용하지 않고 `@Bean`만 사용하면 CGILB를 사용하지 않아서 싱글톤을 보장하지 못하지만 빈으로 등록되는 것은 동일합니다.

<br>

## 컴포넌트 스캔과 의존관계 자동 주입





### 컴포넌트 스캔

스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공합니다.

`@ComponentScan`은 `@Component`을 포함하는 모든 클래스를 스캔하여 스프링 빈으로 등록합니다. (e.g. `@Controller`, `@Service`, `@Repository`)

기본적으로 `@ComponentScan`이 붙은 설정 정보 클래스의 패키지가 시작 위치가 됩니다.

> @Repository
>
> 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환 해줍니다.
> DB에서 예외가 터지면 서비스 계층까지 올라오는데 이때 DB를 변경하면 예외 자체가 바뀌는 문제가 있습니다. 이를 해결하기 위해 스프링이 예외를 추상화해서 반환해줍니다. (@Repository)

### 의존관계 자동 주입

컴포넌트 스캔을 사용하면 빈이 자동으로 등록되기 때문에 의존관계를 클래스 안에서 해결해야 합니다.

`@Autowired`를 사용하면 스프링 컨테이너가 관리하는 스프링 빈을 찾아서 주입합니다.

`@Autowired`는 타입을 기준으로 의존관계를 주입합니다.

생성자가 하나만 있으면 `@Autowired`를 생략해도 자동으로 주입됩니다. (스프링 빈에만 해당)

<br>

## 조회 빈이 두 개 이상일 때

`@Autowired`는 타입으로 매칭하고 결과가 2개 이상이면 필드명, 파라미터 명으로 빈 이름을 매칭합니다.

### @Qualifier

`@Qualifier`로 추가 구분자를 붙여 주입할 빈을 선택할 수 있습니다.

```java
@Component
@Qualifier("mainDiscountPolicy") // 빈 등록시 추가 구분자를 붙여준다.
public class RateDiscountPolicy implements DiscountPolicy {}


public class OrderServiceImpl implements OrderService {
    private final DiscountPolicy discountPolicy;
    
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, 
                            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy // 추가 구분자를 붙여준다.
    ) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

### @Primary

`@Primary`를 사용하면 우선순위를 정할 수 있습니다. 여러 빈이 매칭되면 `@Primary`가 우선권을 가집니다.

```java
@Component
@Primary
public class MainDiscountPolicy implements DiscountPolicy {}
```

### @Primary, @Qualifier 활용

코드에서 자주 사용하는 스프링 빈은 `@Primary`를 사용하고, 특별한 기능으로 가끔 사용하는 스프링 빈은 `@Qualifier`를 지정해서 명시적으로 획득하는 방식으로 사용하면 코드를 깔끔하게 유지할 수 있습니다.

우선 순위는 `@Primary`보다 `@Qualifier`가 우선권이 더 높습니다.

### Qualifier 전용 어노테이션 직접 만들기

`@Qualifier("mainDiscountPolicy")`를 사용하면 문자를 적기 때문에 컴파일시 타입 체크가 안됩니다.

이런 문제를 어노테이션을 만들어 해결할 수 있습니다.

```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}


public class OrderServiceImpl implements OrderService {
    private final DiscountPolicy discountPolicy;
    
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, 
                            @MainDiscountPolicy DiscountPolicy discountPolicy // 추가 구분자를 붙여준다.
    ) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```


의도적으로 정말 해당 타입의 스프링 빈이 다 필요한 경우도 있다.
예를 들어서 할인 서비스를 제공하는데, 클라이언트가 할인의 종류(rate, fix)를 선택할 수 있다고
가정해보자. 스프링을 사용하면 소위 말하는 전략 패턴을 매우 간단하게 구현할 수 있다.

Map<String, DiscountPolicy> : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로
DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
List<DiscountPolicy> : DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다



애플리케이션은 크게 업무 로직과 기술 지원 로직으로 나눌 수 있다.
업무 로직 빈: 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는
리포지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경된다.
기술 지원 빈: 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용된다. 데이터베이스 연결이나,
공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.
업무 로직은 숫자도 매우 많고, 한번 개발해야 하면 컨트롤러, 서비스, 리포지토리 처럼 어느정도 유사한
패턴이 있다. 이런 경우 자동 기능을 적극 사용하는 것이 좋다. 보통 문제가 발생해도 어떤 곳에서 문제가
발생했는지 명확하게 파악하기 쉽다.
기술 지원 로직은 업무 로직과 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게
영향을 미친다. 그리고 업무 로직은 문제가 발생했을 때 어디가 문제인지 명확하게 잘 드러나지만, 기술 지원
로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많다. 그래서 이런 기술 지원 로직들은
가급적 수동 빈 등록을 사용해서 명확하게 드러내는 것이 좋다.
애플리케이션에 광범위하게 영향을 미치는 기술 지원 객체는 수동 빈으로 등록해서 딱! 설정 정보에 바로
나타나게 하는 것이 유지보수 하기 좋다

즈니스 로직 중에서 다형성을 적극 활용할 때
의존관계 자동 주입 - 조회한 빈이 모두 필요할 때, List, Map을 다시 보자.
DiscountService 가 의존관계 자동 주입으로 Map<String, DiscountPolicy> 에 주입을 받는 상황을
생각해보자. 여기에 어떤 빈들이 주입될 지, 각 빈들의 이름은 무엇일지 코드만 보고 한번에 쉽게 파악할 수
있을까? 내가 개발했으니 크게 관계가 없지만, 만약 이 코드를 다른 개발자가 개발해서 나에게 준 것이라면
어떨까?
자동 등록을 사용하고 있기 때문에 파악하려면 여러 코드를 찾아봐야 한다.
이런 경우 수동 빈으로 등록하거나 또는 자동으로하면 특정 패키지에 같이 묶어두는게 좋다! 핵심은 딱 보고
이해가 되어야 한다!
