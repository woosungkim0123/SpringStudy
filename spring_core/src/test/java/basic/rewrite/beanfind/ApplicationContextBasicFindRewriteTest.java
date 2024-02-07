package basic.rewrite.beanfind;

import basic.rewrite.AppConfigRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextBasicFindRewriteTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigRewrite.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberServiceRewrite memberService = ac.getBean("memberServiceRewrite", MemberServiceRewrite.class);
        assertThat(memberService).isInstanceOf(MemberServiceRewriteImpl.class);
    }

    @Test
    @DisplayName("이름없이 타입으로 조회")
    void findBeanByType() {
        MemberServiceRewrite memberService = ac.getBean(MemberServiceRewrite.class);
        assertThat(memberService).isInstanceOf(MemberServiceRewriteImpl.class);
    }
}
