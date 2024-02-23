package com.example.aop.pointcut;

import com.example.aop.member.MemberService;
import com.example.aop.member.annotation.ClassAop;
import com.example.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("test!!");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* com.example.aop.member..*.*(..))")
        private void allMember() {}

        // 매개변수를 가져올 수 있음
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] arg={}", arg1);
            return joinPoint.proceed();
        }

        // 매개변수를 가져올 수 있음
        @Around("allMember() && args(argument,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object argument) throws Throwable {
            log.info("[logArgs2] arg={}", argument);
            return joinPoint.proceed();
        }

        // 매개변수를 가져올 수 있음
        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        @Before("allMember() && args(arg,..)")
        public void notCall(int arg) { // 타입이 맞지 않아 호출되지 않음
            log.info("[notCall] arg={}", arg);
        }

        /**
         * this나 target는 대상 타입을 직접 지정함 (객체가 넘어옴)
         * 차이는 this는 프록시 객체, target은 타겟 객체
         */
        // 프록시 객체를 가져올 수 있음
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // 타겟 객체를 가져올 수 있음
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // 타입의 애노테이션을 가져올 수 있음
        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        // 타입의 애노테이션을 가져올 수 있음
        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        // 메소드의 애노테이션을 가져올 수 있음(안에 값도 가져올 수 있음)
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
