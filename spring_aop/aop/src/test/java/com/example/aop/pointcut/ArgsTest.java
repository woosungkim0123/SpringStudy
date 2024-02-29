package com.example.aop.pointcut;


import com.example.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ArgsTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    /**
     * args
     */
    //hello(String)과 매칭
    @CsvSource({
            "args(String)",
            "args(Object)",
            "args(..)",
            "args(*)"
    })
    @ParameterizedTest
    void args1(String expression) {
        pointcut.setExpression(expression);
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // ,로 csvSource 인식 불가 -> 테스트 분리
    @Test
    void args2() {
        pointcut.setExpression("args(String,..)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터가 비어있는 경우
    @Test
    void args3() {
        pointcut.setExpression("args()");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }



}
