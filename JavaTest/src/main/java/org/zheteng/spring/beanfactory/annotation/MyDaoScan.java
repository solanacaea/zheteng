package org.zheteng.spring.beanfactory.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.zheteng.spring.beanfactory.common.MyImportBeanDefinitionRegistar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportBeanDefinitionRegistar.class)
public @interface MyDaoScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
