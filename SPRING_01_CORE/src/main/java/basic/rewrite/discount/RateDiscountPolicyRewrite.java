package basic.rewrite.discount;

import basic.rewrite.annotation.MainDiscountPolicyRewrite;
import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicyRewrite
public class RateDiscountPolicyRewrite implements DiscountPolicyRewrite {

    private int discountPercent = 10;

    @Override
    public int discount(MemberRewrite member, int price) {
        if (member.getGrade() == GradeRewrite.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }

    }
}
