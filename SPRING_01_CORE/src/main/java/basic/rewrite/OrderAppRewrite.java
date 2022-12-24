package basic.rewrite;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import basic.rewrite.order.OrderRewrite;
import basic.rewrite.order.OrderServiceRewrite;
import basic.rewrite.order.OrderServiceRewriteImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderAppRewrite {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigRewrite.class);

        MemberServiceRewrite memberService = applicationContext.getBean("memberServiceRewrite", MemberServiceRewrite.class);
        OrderServiceRewrite orderService = applicationContext.getBean("orderServiceRewrite", OrderServiceRewrite.class);

        long memberId = 1L;
        MemberRewrite member = new MemberRewrite(memberId, "woosung", GradeRewrite.VIP);
        memberService.join(member);

        OrderRewrite order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);

    }
}
