package org.zheteng.spring.beanfactory;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.zheteng.spring.beanfactory.annotation.MyDaoScan;
import org.zheteng.spring.beanfactory.service.UserBeanService;

@Configuration
@ComponentScan(basePackageClasses = UserBeanService.class)
@MyDaoScan(basePackages = { "org.zheteng.spring.beanfactory.dao" })
public class BeanConfig {

    /**
     *
     * 这种方式可以创建bean，但不推荐，low
     */
//    @Bean
//    public UserDao userDao() throws Exception {
//        MyFactoryBean fb = new MyFactoryBean();
//        fb.setClassInterface(UserDao.class);
//        return (UserDao) fb.getObject();
//    }
}
