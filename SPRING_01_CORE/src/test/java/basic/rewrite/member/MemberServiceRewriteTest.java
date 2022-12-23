package basic.rewrite.member;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberServiceRewriteTest {

    MemberServiceRewrite memberService = new MemberServiceRewriteImpl();

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