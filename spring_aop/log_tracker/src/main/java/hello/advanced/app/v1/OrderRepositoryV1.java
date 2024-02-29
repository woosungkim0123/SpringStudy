package hello.advanced.app.v1;

import hello.advanced.app.common.TraceStatus;
import hello.advanced.app.v1.trace.TraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final TraceV1 trace;

    public void save(String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepositoryV1.save(()");

            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외발생");
            }

            sleep(1000);

            trace.end(status);

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
