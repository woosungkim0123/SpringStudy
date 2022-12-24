package basic.rewrite;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberAppRewrite {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigRewrite.class);

        MemberServiceRewrite memberService = applicationContext.getBean("memberServiceRewrite", MemberServiceRewrite.class);

        MemberRewrite member = new MemberRewrite(1L, "woosung", GradeRewrite.VIP);
        memberService.join(member);
        System.out.println("new member = " + member.getName());

        MemberRewrite findMember = memberService.findById(1L);
        System.out.println("findMember = " + findMember.getName());

    }
}
