package hello.advanced.app.v6;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.TraceTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV6 {

    private final OrderServiceV6 orderService;
    private final TraceTemplate template;

    public OrderControllerV6(OrderServiceV6 orderService, @Qualifier("threadLocalLogTrace") LogTrace logTrace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(logTrace);
    }

    @GetMapping("/v6/request")
    public String request(String itemId) {
        return template.execute("OrderController.request()", () -> {
            orderService.orderItem(itemId);
            return "ok (version 6)";
        });
    }
}
