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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfigRewrite {

    @Bean
    public MemberServiceRewrite memberServiceRewrite() {
        return new MemberServiceRewriteImpl(memberRepositoryRewrite());
    }

    @Bean
    public OrderServiceRewrite orderServiceRewrite() {
        return new OrderServiceRewriteImpl(
                memberRepositoryRewrite(),
                discountPolicyRewrite()
        );
    }
    @Bean
    public MemberRepositoryRewrite memberRepositoryRewrite() {
        return new MemoryMemberRepositoryRewrite();
    }
    @Bean
    public DiscountPolicyRewrite discountPolicyRewrite() {
        return new RateDiscountPolicyRewrite();
    }

}
