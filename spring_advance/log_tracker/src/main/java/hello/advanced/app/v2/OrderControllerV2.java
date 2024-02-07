package hello.advanced.app.v2;

import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.v2.trace.TraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final TraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId); // 로그를 넘기는 파라미터 추가
            trace.end(status);
            return "ok (version 2)";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
