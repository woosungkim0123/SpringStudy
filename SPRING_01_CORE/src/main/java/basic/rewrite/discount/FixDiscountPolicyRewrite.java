package basic.rewrite.discount;

import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicyRewrite implements DiscountPolicyRewrite {

    private int discountFixAmount = 1000;

    @Override
    public int discount(MemberRewrite member, int price) {

        if(member.getGrade() == GradeRewrite.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
