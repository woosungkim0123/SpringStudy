package hello.advanced.app.v4;

import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.common.LogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    public OrderControllerV4(OrderServiceV4 orderService, @Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.orderService = orderService;
        this.trace = trace;
    }

    @GetMapping("/v4/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok (version 4)";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
