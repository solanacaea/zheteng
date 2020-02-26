package org.zheteng.norepeatsubmit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
public class NoRepeatSubmitAspect {

    @Autowired
    private HttpServletRequest request;

	private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(5, TimeUnit.SECONDS)
			.build();
	
	@Around("execution(public * *(..)) && @annotation(org.zheteng.norepeatsubmit.NoRepeatSubmit)")
    public Object nonrepeat(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
//        NoRepeatSubmit annoation = method.getAnnotation(NoRepeatSubmit.class);
//        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        String remoteAddr = request.getRemoteAddr();
        String key = getCacheKey(method, remoteAddr);
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                throw new RuntimeException("请勿重复请求!");
            }
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            ExceptionUtils.getStackTrace(throwable);
            throw new RuntimeException("服务器异常!");
        } finally {
             //CACHES.invalidate(key);
        }
	}
	
	private String getCacheKey(Method method, String arg) {
        return method.getName() + arg;
    }
}
