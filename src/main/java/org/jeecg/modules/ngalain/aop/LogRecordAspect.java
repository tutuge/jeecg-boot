//package org.jeecg.modules.ngalain.aop;
//
//import com.alibaba.fastjson.JSONObject;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//
//// 暂时注释掉，提高系统性能
//@Aspect   //定义一个切面
//@Configuration
//public class LogRecordAspect {
//    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);
//
//    // 定义切点Pointcut
//    @Pointcut("execution(public * org.jeecg.modules..controller..*Controller.*(..))")
//    public void excudeService() {
//    }
//
//    @Around("excudeService()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        long start = System.currentTimeMillis();
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        Object[] args = pjp.getArgs();
//        logger.info("请求开始 url: {}", url);
//        logger.info("method: {}", method);
//        logger.info("uri: {}", uri);
//        logger.info("params: {} ", queryString);
//        logger.info("args {}", args);
//
//        // result的值就是被拦截方法的返回值
//        Object result = pjp.proceed();
//        long end = System.currentTimeMillis();
//        logger.info("请求结束，controller的返回值是 " + JSONObject.toJSONString(result) + "\n"
//                + "请求执行时间耗时: {} 毫秒", end - start);
//        return result;
//    }
//}
