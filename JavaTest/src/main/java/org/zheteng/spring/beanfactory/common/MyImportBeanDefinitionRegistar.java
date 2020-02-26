package org.zheteng.spring.beanfactory.common;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.zheteng.spring.beanfactory.annotation.MyDao;
import org.zheteng.spring.beanfactory.annotation.MyDaoScan;
import org.zheteng.spring.beanfactory.proxy.MyFactoryBean;
import org.zheteng.utils.PackageScaner;

import java.util.*;
import java.util.stream.Collectors;

public class MyImportBeanDefinitionRegistar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        Set<String> packages = getPackagesToScan(importingClassMetadata);
        Collection<Class<?>> classes = packages.stream().flatMap(p ->
                    PackageScaner.scanClasses(p, MyDao.class).stream())
                .collect(Collectors.toCollection(HashSet::new));

        classes.forEach(clazz -> {
            BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class);
            AbstractBeanDefinition beanDefinition = beanBuilder.getBeanDefinition();
            MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
            propertyValues.add("classInterface", clazz);
            registry.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
        });
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(MyDaoScan.class.getName()));
        String[] packages = attrs.getStringArray("basePackages");
        Class<?>[] classes = attrs.getClassArray("basePackageClasses");
        Set<String> packagesToScan = new LinkedHashSet<>();
        packagesToScan.addAll(Arrays.asList(packages));
        for (Class<?> clazz : classes) {
            packagesToScan.add(ClassUtils.getPackageName(clazz));
        }
        if (packagesToScan.isEmpty()) {
            return Collections.singleton(
                    ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }


}
