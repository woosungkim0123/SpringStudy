package basic.rewrite;

import basic.rewrite.discount.FixDiscountPolicyRewrite;
import basic.rewrite.member.MemberServiceRewrite;
import basic.rewrite.member.MemberServiceRewriteImpl;
import basic.rewrite.member.MemoryMemberRepositoryRewrite;
import basic.rewrite.order.OrderServiceRewrite;
import basic.rewrite.order.OrderServiceRewriteImpl;

public class AppConfigRewrite {

    public MemberServiceRewrite memberServiceRewrite() {
        return new MemberServiceRewriteImpl(new MemoryMemberRepositoryRewrite());
    }

    public OrderServiceRewrite orderServiceRewrite() {
        return new OrderServiceRewriteImpl(
                new MemoryMemberRepositoryRewrite(),
                new FixDiscountPolicyRewrite()
        );
    }


}
