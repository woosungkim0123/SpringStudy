package com.example.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callService;

    @Autowired
    public void setCallService(CallServiceV1 callService) {
        this.callService = callService;
    }

    public void external() {
        log.info("call external");
        callService.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
