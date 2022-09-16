package basic.core.order;

import basic.core.discount.FixDiscountPolicy;
import basic.core.member.MemberRepository;
import basic.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final FixDiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        return null;
    }
}
