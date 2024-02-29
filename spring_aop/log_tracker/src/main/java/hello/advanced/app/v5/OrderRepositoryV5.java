package hello.advanced.app.v5;

import hello.advanced.app.common.LogTrace;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {
    private final LogTrace trace;

    public OrderRepositoryV5(@Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.trace = trace;
    }

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                if(itemId.equals("ex")) {
                    throw new IllegalStateException("예외발생");
                }
                sleep(1000);
                return null;
            }
        };
        template.execute("OrderRepository.save(()");
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
