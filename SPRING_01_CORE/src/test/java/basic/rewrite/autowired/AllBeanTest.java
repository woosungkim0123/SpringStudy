package basic.rewrite.autowired;

import basic.rewrite.AutoAppConfigRewrite;
import basic.rewrite.discount.DiscountPolicyRewrite;
import basic.rewrite.member.GradeRewrite;
import basic.rewrite.member.MemberRewrite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfigRewrite.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        MemberRewrite member = new MemberRewrite(1L, "userA", GradeRewrite.VIP);

        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicyRewrite");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicyRewrite");

        assertThat(rateDiscountPrice).isEqualTo(2000);

    }

    static class DiscountService {

        private final Map<String, DiscountPolicyRewrite> policyMap;
        private final List<DiscountPolicyRewrite> policies;

        @Autowired
        DiscountService(Map<String, DiscountPolicyRewrite> policyMap, List<DiscountPolicyRewrite> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(MemberRewrite member, int price, String discountCode) {
            DiscountPolicyRewrite discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}


