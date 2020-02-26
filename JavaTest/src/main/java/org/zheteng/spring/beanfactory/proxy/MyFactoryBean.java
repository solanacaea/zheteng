package org.zheteng.spring.beanfactory.proxy;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.zheteng.spring.beanfactory.common.DefaultDao;
import org.zheteng.spring.beanfactory.common.SqlProcessor;

import java.lang.reflect.Proxy;

@Data
public class MyFactoryBean implements FactoryBean<Object> {

    private Class<?> classInterface;
    private SqlProcessor processor;//这个相当于hibernate EntityManager，jdbc SqlSessionFactory....

    @Override
    public Object getObject() throws Exception {
        Class<?>[] classes = new Class[]{classInterface};
        return Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(),
                classes,
                new DefaultDao());
    }

    @Override
    public Class<?> getObjectType() {
        return classInterface;
    }
}
