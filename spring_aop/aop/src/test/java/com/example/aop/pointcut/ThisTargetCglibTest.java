package com.example.aop.pointcut;


import com.example.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ThisTargetCglibTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // cglib proxy
public class ThisTargetCglibTest {

    @Autowired
    MemberService memberService;
    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("test!");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        //부모 타입 허용
        @Around("this(com.example.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //부모 타입 허용
        @Around("target(com.example.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws
                Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // this: 스프링 AOP 프록시 객체 대상
        // CGLIB 프록시는 구현 클래스를 기반으로 생성되므로 구현 클래스를 알 수 있음
        @Around("this(com.example.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //target: 실제 target 객체 대상
        @Around("target(com.example.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
