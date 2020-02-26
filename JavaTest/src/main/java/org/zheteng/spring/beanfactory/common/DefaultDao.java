package org.zheteng.spring.beanfactory.common;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@CommonsLog
public class DefaultDao implements InvocationHandler {

    private SqlProcessor processor;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Query query = method.getAnnotation(Query.class);
        Class<?> returnType = method.getReturnType();

        Map<String, Object> paraMap = new HashMap<>();
        Annotation[][] annotation2d = method.getParameterAnnotations();
        for (Annotation[] annotation1d : annotation2d) {
            int argIndex = ArrayUtils.indexOf(annotation2d, annotation1d);
            Annotation param = annotation1d[0];
            if (param instanceof Param) {
                String key = ((Param) param).value();
                Object value = args[argIndex];
                paraMap.put(key, value);
            }
        }
        log.info("sql: " + query.value());
        log.info("parameter: " + paraMap);
//        return processor.execute(query, paraMap);
        return null;
    }

}
