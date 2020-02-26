package org.zheteng.spring.aop;

import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@CommonsLog
public class AppAspect {

    //增强N个方法的集合
    @Pointcut("execution(* org.zheteng.spring.aop.dao..*.*(..))")
    private void pointCut() {}

    @Before("pointCut()")//切点的时机
    public void advice() {
        log.info("=====================================");
        log.info("进入切面");
    }

}

