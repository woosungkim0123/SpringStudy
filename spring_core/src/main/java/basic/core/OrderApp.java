package basic.core;

import basic.core.member.Grade;
import basic.core.member.Member;
import basic.core.member.MemberService;
import basic.core.member.MemberServiceImpl;
import basic.core.order.Order;
import basic.core.order.OrderService;
import basic.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        // Spring으로 전환
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        // 일단 VIP회원 만들고
        Long memberId = 1L;
        Member member = new Member(memberId, "woosung", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "샴푸", 10000);

        System.out.println("order = " + order);
        System.out.println("order = " + order.calculatePrice());
    }
}
