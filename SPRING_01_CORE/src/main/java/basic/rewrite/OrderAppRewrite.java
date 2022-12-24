package basic.rewrite;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import basic.rewrite.order.OrderRewrite;
import basic.rewrite.order.OrderServiceRewriteImpl;

public class OrderAppRewrite {

    public static void main(String[] args) {
        MemberServiceRewriteImpl memberService = new MemberServiceRewriteImpl();
        OrderServiceRewriteImpl orderService = new OrderServiceRewriteImpl();

        long memberId = 1L;
        MemberRewrite member = new MemberRewrite(memberId, "woosung", GradeRewrite.VIP);
        memberService.join(member);

        OrderRewrite order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);

    }
}
