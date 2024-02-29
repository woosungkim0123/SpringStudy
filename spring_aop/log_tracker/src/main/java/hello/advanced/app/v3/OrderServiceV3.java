package hello.advanced.app.v3;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.TraceStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV3 {
    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public OrderServiceV3(OrderRepositoryV3 orderRepository, @Qualifier("traceV3") LogTrace trace) {
        this.orderRepository = orderRepository;
        this.trace = trace;
    }

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
