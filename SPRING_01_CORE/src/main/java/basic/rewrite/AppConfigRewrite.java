package basic.rewrite;

import basic.rewrite.discount.DiscountPolicyRewrite;
import basic.rewrite.discount.FixDiscountPolicyRewrite;
import basic.rewrite.discount.RateDiscountPolicyRewrite;
import basic.rewrite.member.MemberRepositoryRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import basic.rewrite.member.MemoryMemberRepositoryRewrite;
import basic.rewrite.order.OrderServiceRewrite;
import basic.rewrite.order.OrderServiceRewriteImpl;

public class AppConfigRewrite {

    public MemberServiceRewrite memberServiceRewrite() {
        return new MemberServiceRewriteImpl(memberRepositoryRewrite());
    }

    public OrderServiceRewrite orderServiceRewrite() {
        return new OrderServiceRewriteImpl(
                memberRepositoryRewrite(),
                discountPolicyRewrite()
        );
    }

    public MemberRepositoryRewrite memberRepositoryRewrite() {
        return new MemoryMemberRepositoryRewrite();
    }

    public DiscountPolicyRewrite discountPolicyRewrite() {
        return new RateDiscountPolicyRewrite();
    }

}
