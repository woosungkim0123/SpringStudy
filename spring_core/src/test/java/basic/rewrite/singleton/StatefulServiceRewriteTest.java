package basic.rewrite.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceRewriteTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulServiceRewrite statefulService1 = ac.getBean(StatefulServiceRewrite.class);
        StatefulServiceRewrite statefulService2 = ac.getBean(StatefulServiceRewrite.class);

        // Thread1
        statefulService1.order("woosung", 10000);
        // Thread2
        statefulService2.order("woosung2", 20000);

        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulServiceRewrite statefulService() {
            return new StatefulServiceRewrite();
        }
    }
}