package hello.advanced.app.v6;

import hello.advanced.app.common.LogTrace;
import hello.advanced.app.common.TraceTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV6 {

    private final TraceTemplate template;

    public OrderRepositoryV6(@Qualifier("threadLocalLogTrace") LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {

        template.execute("OrderRepository.save(()", () -> {
            if(itemId.equals("ex")) {
                throw new IllegalStateException("예외발생");
            }
            sleep(1000);
            return null;
        });
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
