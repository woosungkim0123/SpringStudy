package com.example.aop.proxyvs;

import com.example.aop.member.MemberService;
import com.example.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ProxyDIAspect.class)
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
public class ProxyDITest {

    @Autowired
    MemberService memberService; //JDK 동적 프록시 OK, CGLIB OK

    /**
     * 동적 프록시 사용시 예외 발생
     * - 빈에 주입되길 기대하는 타입은 MemberServiceImpl이지만 실제로 넘어온 타입은 Proxy 타입이라 예외를 발생시킨다.
     * Bean named 'memberServiceImpl' is expected to be
     * of type 'com.example.aop.member.MemberServiceImpl' but was actually of type
     * 'com.sun.proxy.$Proxy54'
     */
    @Autowired
    MemberServiceImpl memberServiceImpl; //JDK 동적 프록시 X, CGLIB OK

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
