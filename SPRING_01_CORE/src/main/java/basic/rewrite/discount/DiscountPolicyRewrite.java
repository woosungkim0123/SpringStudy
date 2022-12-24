package basic.rewrite.discount;

import basic.rewrite.member.MemberRewrite;

public interface DiscountPolicyRewrite {

    int discount(MemberRewrite member, int price);

}
