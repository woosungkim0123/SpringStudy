package basic.rewrite.member;


import basic.rewrite.AppConfigRewrite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberServiceRewriteTest {

    MemberServiceRewrite memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfigRewrite appConfig = new AppConfigRewrite();
        memberService = appConfig.memberServiceRewrite();
    }

    @Test
    void join() {
        // given
        MemberRewrite member = new MemberRewrite(1L, "woosung", GradeRewrite.VIP);

        // when
        memberService.join(member);
        MemberRewrite findMember = memberService.findById(1L);

        // then
        assertThat(member).isEqualTo(findMember);
    }

}