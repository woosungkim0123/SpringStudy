package com.example.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    private final ApplicationContext applicationContext; // 방법1. 기능이 너무 많음
    private final ObjectProvider<CallServiceV2> callServiceProvider; // 방법2

    public CallServiceV2(ApplicationContext applicationContext, ObjectProvider<CallServiceV2> callServiceProvider) {
        this.applicationContext = applicationContext;
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        //CallServiceV2 callService = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callService = callServiceProvider.getObject();
        log.info("call external");
        callService.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
