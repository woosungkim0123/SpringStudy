package basic.rewrite.xml;

import basic.rewrite.member.MemberServiceRewrite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContextRewrite {

    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfigRewrite.xml");
        MemberServiceRewrite memberService = ac.getBean("memberService", MemberServiceRewrite.class);
        assertThat(memberService).isInstanceOf(MemberServiceRewrite.class);
    }
}
