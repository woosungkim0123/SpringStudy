package basic.rewrite.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatelessServiceRewriteTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessServiceRewrite statelessService1 = ac.getBean(StatelessServiceRewrite.class);
        StatelessServiceRewrite statelessService2 = ac.getBean(StatelessServiceRewrite.class);

        // Thread1
        int userAPrice = statelessService1.order("woosung", 10000);
        // Thread2
        int userBPrice = statelessService2.order("woosung2", 20000);

        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);
        assertThat(userAPrice).isEqualTo(10000);
        assertThat(userBPrice).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatelessServiceRewrite statelessService() {
            return new StatelessServiceRewrite();
        }
    }
}