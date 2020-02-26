package org.zheteng.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "org.zheteng.spring.aop")
@EnableAspectJAutoProxy
public class AppConfig {

}
