package hello.advanced.app.v3;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.TraceStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    public OrderControllerV3(OrderServiceV3 orderService, @Qualifier("traceV3") LogTrace trace) {
        this.orderService = orderService;
        this.trace = trace;
    }

    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok (version 3)";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
