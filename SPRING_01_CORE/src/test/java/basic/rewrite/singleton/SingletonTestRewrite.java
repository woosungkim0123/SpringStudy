package basic.rewrite.singleton;

import basic.rewrite.AppConfigRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
