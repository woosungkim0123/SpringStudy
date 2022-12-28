package basic.rewrite.scan;


import basic.rewrite.AutoAppConfigRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AutoAppConfigRewriteTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfigRewrite.class);

        MemberServiceRewrite memberService = ac.getBean(MemberServiceRewrite.class);
        assertThat(memberService).isInstanceOf(MemberServiceRewrite.class);
    }
}