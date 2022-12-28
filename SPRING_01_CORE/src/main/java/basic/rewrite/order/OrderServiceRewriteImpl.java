package basic.rewrite.order;


import basic.rewrite.discount.DiscountPolicyRewrite;
import basic.rewrite.member.MemberRepositoryRewrite;
import basic.rewrite.member.MemberRewrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceRewriteImpl implements OrderServiceRewrite {

    private final MemberRepositoryRewrite memberRepository;
    private final DiscountPolicyRewrite discountPolicy;

    @Autowired
    public OrderServiceRewriteImpl(MemberRepositoryRewrite memberRepository, DiscountPolicyRewrite discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public OrderRewrite createOrder(Long memberId, String itemName, int itemPrice) {
        MemberRewrite member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new OrderRewrite(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepositoryRewrite getMemberRepository() {
        return memberRepository;
    }
}
