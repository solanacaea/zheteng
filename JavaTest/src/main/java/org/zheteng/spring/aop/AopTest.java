package org.zheteng.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.zheteng.spring.aop.dao.UserDao;

public class AopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        //BeanFactory
        UserDao userDao = context.getBean(UserDao.class);
        userDao.query();

    }
}
