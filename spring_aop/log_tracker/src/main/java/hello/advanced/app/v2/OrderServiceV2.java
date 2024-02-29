package hello.advanced.app.v2;

import hello.advanced.app.common.TraceId;
import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.v2.trace.TraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final TraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderServiceV1.orderItem()");
            orderRepository.save(status.getTraceId(), itemId); // 로그를 넘기는 파라미터 추가
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }

}
