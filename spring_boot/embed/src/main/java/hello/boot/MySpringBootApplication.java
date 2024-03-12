package hello.boot;


import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 클래스에 붙일 수 있도록
@Retention(RetentionPolicy.RUNTIME)
@Documented // javadoc으로 문서를 만들때 설명도 포함하도록 지정
@ComponentScan // 붙은 클래스 패키지 포함 하위 패키지를 스캔
public @interface MySpringBootApplication {
}
