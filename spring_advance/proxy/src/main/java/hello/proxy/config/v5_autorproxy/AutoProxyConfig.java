package hello.proxy.config.v5_autorproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.LogTraceConfig;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppV1Config.class, AppV2Config.class, LogTraceConfig.class})
@Configuration
public class AutoProxyConfig {

    // 어드바이저만 등록하면 끝 (자동 프록시 생성기가 알아서 프록시 생성)
    // @Bean // advisor2에 중복되서 주석처리
    public Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "save*", "order*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    //@Bean // advisor3에 중복되서 주석처리
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        /**
         * execution(* hello.proxy.app..*(..))**
         *
         * - *: 모든 타입 반환
         * - hello.proxy.app..: 해당 패키지와 그 하위 패키지
         * - *(..): *는 모든 메서드, (..)는 모든 매개변수
         */
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        //advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // noLog 적용 안되도록 수정
    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        //advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
