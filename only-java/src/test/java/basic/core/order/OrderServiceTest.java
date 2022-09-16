package basic.core.order;

import basic.core.member.Grade;
import basic.core.member.Member;
import basic.core.member.MemberService;
import basic.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "woosung", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "샴푸", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
