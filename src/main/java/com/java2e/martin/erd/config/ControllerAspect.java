package com.java2e.martin.erd.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/27
 * @describtion ControllerAspect
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {
    /**
     * 定义切入点，切入点为com.java2e.martin.erd.controller中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.java2e.martin.erd.controller.*.*(..)))")
    public void ControllerAspect() {

    }

    /**
     * @description 在连接点执行之前执行的通知
     */
    @Around("ControllerAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        //这里可以获取到get请求的参数和其他信息
        log.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        //重点 这里就是获取@RequestBody参数的关键  调试的情况下 可以看到o变量已经获取到了请求的参数
        Object[] objects = pjp.getArgs();
        if (objects.length > 0 && objects[0] instanceof Map) {
            Map map = (Map) objects[0];
            String driverClassName = (String) map.get("driverClassName");
            map.put("driver_class_name", driverClassName);
            objects[0] = map;
            // result的值就是被拦截方法的返回值
            try {
                return pjp.proceed(objects);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw throwable;
            }
        } else {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw throwable;
            }
        }
    }


}
