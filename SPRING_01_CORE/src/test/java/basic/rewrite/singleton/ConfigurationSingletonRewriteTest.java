package basic.rewrite.singleton;


import basic.rewrite.AppConfigRewrite;
import basic.rewrite.member.MemberRepositoryRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import basic.rewrite.order.OrderServiceRewriteImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonRewriteTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigRewrite.class);

        MemberServiceRewriteImpl memberService = ac.getBean("memberServiceRewrite", MemberServiceRewriteImpl.class);
        OrderServiceRewriteImpl orderService = ac.getBean("orderServiceRewrite", OrderServiceRewriteImpl.class);
        MemberRepositoryRewrite memberRepository = ac.getBean("memberRepositoryRewrite", MemberRepositoryRewrite.class);


        MemberRepositoryRewrite memberRepository1 = memberService.getMemberRepository();
        MemberRepositoryRewrite memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberService -> memberServiceRepository = " + memberRepository1);
        System.out.println("orderService -> memberServiceRepository = " + memberRepository2);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigRewrite.class);
        AppConfigRewrite bean = ac.getBean(AppConfigRewrite.class);

        System.out.println("bean = " + bean.getClass());
    }
}
