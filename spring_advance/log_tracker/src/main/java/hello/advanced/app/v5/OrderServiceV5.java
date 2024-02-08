package hello.advanced.app.v5;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.AbstractTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {
    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, @Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.orderRepository = orderRepository;
        this.trace = trace;
    }

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
}
