package basic.rewrite;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;

public class MemberAppRewrite {
    public static void main(String[] args) {

        AppConfigRewrite appConfig = new AppConfigRewrite();
        MemberServiceRewrite memberService = appConfig.memberServiceRewrite();
        MemberRewrite member = new MemberRewrite(1L, "woosung", GradeRewrite.VIP);
        memberService.join(member);
        System.out.println("new member = " + member.getName());

        MemberRewrite findMember = memberService.findById(1L);
        System.out.println("findMember = " + findMember.getName());

    }
}
