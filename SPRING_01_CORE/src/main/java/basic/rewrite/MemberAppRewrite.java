package basic.rewrite;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;

public class MemberAppRewrite {
    public static void main(String[] args) {

        MemberServiceRewrite memberService = new MemberServiceRewriteImpl();
        MemberRewrite member = new MemberRewrite(1L, "woosung", GradeRewrite.VIP);
        memberService.join(member);
        System.out.println("new member = " + member.getName());

        MemberRewrite findMember = memberService.findById(1L);
        System.out.println("findMember = " + findMember.getName());

    }
}
