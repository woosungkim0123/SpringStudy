package hello.advanced.app.v6;

import hello.advanced.app.common.LogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV6 {

    private final OrderRepositoryV6 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV6(OrderRepositoryV6 orderRepository, @Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
