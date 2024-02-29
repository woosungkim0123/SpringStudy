package hello.advanced.app.v1;

import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.v1.trace.TraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final TraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {
        TraceStatus status = null; // 예외 처리시 status가 필요하기 때문에

        try {
            status = trace.begin("OrderController.request()"); // 로그 시작
            orderService.orderItem(itemId); // 비지니스 로직
            trace.end(status); // 로그 끝

            return "ok (version 1)";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
