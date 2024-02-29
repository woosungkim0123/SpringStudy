package hello.advanced.app.v4;

import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.common.LogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public OrderServiceV4(OrderRepositoryV4 orderRepository, @Qualifier("threadLocalLogTrace") LogTrace trace) {
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
