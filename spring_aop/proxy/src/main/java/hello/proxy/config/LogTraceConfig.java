package hello.proxy.config;


import hello.proxy.trace.LogTrace;
import hello.proxy.trace.ThreadLocalLogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Qualifier("threadLocalLogTrace")
    @Bean
    public LogTrace threadLocalLogTrace() {
        return new ThreadLocalLogTrace();
    }
}
