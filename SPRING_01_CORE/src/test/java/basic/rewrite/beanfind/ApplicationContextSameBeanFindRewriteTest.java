package basic.rewrite.beanfind;


import basic.rewrite.member.MemberRepositoryRewrite;

import basic.rewrite.member.MemoryMemberRepositoryRewrite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ApplicationContextSameBeanFindRewriteTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면 중복오류 발생")
    void findBeanByTypeDuplicate() {
        assertThrows(
                NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepositoryRewrite.class)
        );

    }


    @Test
    @DisplayName("타입 조회시 같은 타입이 둘 이상 있으면, 빈 이름 지정하면 됨")
    void findBeanByName() {
        MemberRepositoryRewrite memberRepository = ac.getBean("memberRepository1", MemberRepositoryRewrite.class);
        assertThat(memberRepository).isInstanceOf(MemberRepositoryRewrite.class);

    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepositoryRewrite> beansOfType = ac.getBeansOfType(MemberRepositoryRewrite.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }
    

    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepositoryRewrite memberRepository1() {
            return new MemoryMemberRepositoryRewrite();
        }

        @Bean
        public MemberRepositoryRewrite memberRepository2() {
            return new MemoryMemberRepositoryRewrite();
        }

    }

}
