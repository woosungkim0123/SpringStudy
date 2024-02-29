package hello.advanced.config;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.v3.trace.TraceV3;
import hello.advanced.app.common.ThreadLocalLogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Qualifier("traceV3")
    @Bean
    public LogTrace traceV3() {
        return new TraceV3();
    }

    @Qualifier("threadLocalLogTrace")
    @Bean
    public LogTrace threadLocalLogTrace() {
        return new ThreadLocalLogTrace();
    }
}
