package basic.rewrite.order;


import basic.rewrite.AppConfigRewrite;
import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceRewriteTest {

    MemberServiceRewrite memberService;
    OrderServiceRewrite orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfigRewrite appConfig = new AppConfigRewrite();
        memberService = appConfig.memberServiceRewrite();
        orderService = appConfig.orderServiceRewrite();
    }



    @Test
    void createOrder() {
        long memberId = 1L;
        MemberRewrite member = new MemberRewrite(memberId, "woosung1", GradeRewrite.VIP);
        memberService.join(member);

        OrderRewrite order = orderService.createOrder(memberId, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);


    }
}
