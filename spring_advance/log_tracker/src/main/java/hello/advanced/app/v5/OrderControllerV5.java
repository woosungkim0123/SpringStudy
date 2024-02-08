package hello.advanced.app.v5;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.AbstractTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {
    private final OrderServiceV5 orderService;
    private final LogTrace trace;

    public OrderControllerV5(OrderServiceV5 orderService, @Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.orderService = orderService;
        this.trace = trace;
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {

        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok (version 5)";
            }
        };
        return template.execute("OrderController.request()");
    }
}
