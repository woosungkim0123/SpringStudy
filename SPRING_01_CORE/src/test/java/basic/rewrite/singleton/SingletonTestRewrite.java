package basic.rewrite.singleton;

import basic.rewrite.AppConfigRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTestRewrite {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfigRewrite appConfig = new AppConfigRewrite();

        MemberServiceRewrite memberService1 = appConfig.memberServiceRewrite();
        MemberServiceRewrite memberService2 = appConfig.memberServiceRewrite();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {

        SingletonServiceRewrite singletonService1 = SingletonServiceRewrite.getInstance();
        SingletonServiceRewrite singletonService2 = SingletonServiceRewrite.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext appConfig = new AnnotationConfigApplicationContext(AppConfigRewrite.class);

        MemberServiceRewrite memberService1 = appConfig.getBean("memberServiceRewrite", MemberServiceRewrite.class);
        MemberServiceRewrite memberService2 = appConfig.getBean("memberServiceRewrite", MemberServiceRewrite.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }

}
