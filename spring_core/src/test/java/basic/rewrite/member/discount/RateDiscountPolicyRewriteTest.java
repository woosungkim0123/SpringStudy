package basic.rewrite.member.discount;


import basic.rewrite.discount.DiscountPolicyRewrite;
import basic.rewrite.discount.RateDiscountPolicyRewrite;
import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyRewriteTest {

    DiscountPolicyRewrite discountPolicy = new RateDiscountPolicyRewrite();

    @Test
    @DisplayName("VIP 10%할인 적용")
    void vip_o() {
        // given
        MemberRewrite member = new MemberRewrite(1L, "woosung", GradeRewrite.VIP);
        // when
        int discount = discountPolicy.discount(member, 15000);
        // then
        assertThat(discount).isEqualTo(1500);
    }

    @Test
    @DisplayName("VIP가 아니면 할인 적용 X")
    void vip_x() {
        //given
        MemberRewrite member = new MemberRewrite(2L, "woosung2", GradeRewrite.BASIC);
        // when
        int discount = discountPolicy.discount(member, 15000);
        // then
        assertThat(discount).isEqualTo(0);
    }
}