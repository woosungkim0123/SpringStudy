package hello.advanced.app.v3;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.TraceStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV3 {
    private final LogTrace trace;

    public OrderRepositoryV3(@Qualifier("traceV3") LogTrace trace) {
        this.trace = trace;
    }

    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save(()");

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
