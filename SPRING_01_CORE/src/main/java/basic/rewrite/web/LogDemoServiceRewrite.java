package basic.rewrite.web;

import basic.rewrite.common.MyLoggerRewrite;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoServiceRewrite {
    private final MyLoggerRewrite myLogger;

    public void logic(String id) {
        myLogger.log("service id = " + id);
    }
}
