package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {
        // 저장

        if(itemId.equals("ex")) {
            throw new IllegalStateException("예외발생");
        }

        sleep(1000); // 상품 저장하는데 1초 걸린다 가정
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
