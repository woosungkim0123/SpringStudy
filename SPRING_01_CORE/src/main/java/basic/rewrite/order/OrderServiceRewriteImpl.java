package basic.rewrite.order;

import basic.rewrite.discount.DiscountPolicyRewrite;
import basic.rewrite.discount.FixDiscountPolicyRewrite;
import basic.rewrite.member.MemberRepositoryRewrite;
import basic.rewrite.member.MemberRewrite;
import basic.rewrite.member.MemoryMemberRepositoryRewrite;

public class OrderServiceRewriteImpl implements OrderServiceRewrite {

    private final MemberRepositoryRewrite memberRepository = new MemoryMemberRepositoryRewrite();
    private final DiscountPolicyRewrite discountPolicy = new FixDiscountPolicyRewrite();

    @Override
    public OrderRewrite createOrder(Long memberId, String itemName, int itemPrice) {
        MemberRewrite member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new OrderRewrite(memberId, itemName, itemPrice, discountPrice);
    }
}
