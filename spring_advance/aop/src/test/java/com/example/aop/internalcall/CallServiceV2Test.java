package com.example.aop.internalcall;

import com.example.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callService; // 프록시

    @Test
    void external() {
        callService.external();
    }

    @Test
    void internal() {
        callService.internal();
    }
}