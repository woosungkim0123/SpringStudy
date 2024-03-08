package basic.core;

import basic.core.member.Grade;
import basic.core.member.Member;
import basic.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        /**
         * 구성 영역과 실행 영역을 분리
         */
        // Spring 없이 실행
        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();

        // Spring으로 전환
        // AppConfig에 있는 설정 정보를 ApplicationContext(Spring Container)에 넘겨주면 생성하고 관리해줍니다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println(member.getName());
        System.out.println(findMember.getName());
    }
}
