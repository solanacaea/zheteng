package org.zheteng.spring.beanfactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.zheteng.spring.beanfactory.service.UserBeanService;

public class BeanFactoryTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfig.class);
        UserBeanService service = ac.getBean(UserBeanService.class);
        service.query("sb123");
    }
}
