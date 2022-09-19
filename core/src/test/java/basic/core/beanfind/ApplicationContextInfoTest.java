package basic.core.beanfind;

import basic.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // 리스트나 배열이 있으면 iter하고 탭하면 for문이 자동으로 완성
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = "  + beanDefinitionName + " object = " + bean);

        }
    }
    @Test
    @DisplayName("어플리케이션 빈 출력")
    void findAppBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

           if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("name = "  + beanDefinitionName + " object = " + bean);
            }

        }
    }
}
