package hello.proxy.config.v2_dynamic_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.LogTraceConfig;
import hello.proxy.config.v2_dynamic_proxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Proxy;

@Import({LogTraceConfig.class})
@Configuration
public class DynamicFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"}; // 메서드 이름 필터링 패턴, 변경

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));

        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(controllerImpl, logTrace, PATTERNS) // 변경
        );

        return proxy; // 프록시 등록
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));

        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceFilterHandler(serviceImpl, logTrace, PATTERNS)
        );

        return proxy; // 프록시 등록
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();

        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceFilterHandler(repositoryImpl, logTrace, PATTERNS)
        );

        return proxy; // 프록시 등록
    }
}
