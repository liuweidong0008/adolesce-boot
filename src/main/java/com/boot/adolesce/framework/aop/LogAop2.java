/*******************************************************************************
 * @(#)LogAop2.java 2019年12月06日 15:03
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.framework.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Application name：</b> LogAop2.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年12月06日 15:03 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Aspect
@Component
@Slf4j
public class LogAop2 {
    /**
     * 用来记录请求进入的时间，防止多线程时出错，这里用了ThreadLocal
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 定义切入点，任意包下以Controller结尾类下面的任意公共方法
     */
    @Pointcut("execution(public * com.boot..*.*Controller.*(..))")
    public void requestLog(){};

    /**
     * 方法之前执行，日志打印请求信息
     * @param joinPoint joinPoint
     *
     * 在目标方法执行之前执行执行的通知。
     * 前置通知方法，可以没有参数，也可以额外接收一个JoinPoint，Spring会自动将该对象传入，代表当前的连接点，通过该对象可以获取目标对象 和 目标方法相关的信息。
     * 注意，如果接收JoinPoint，必须保证其为方法的第一个参数，否则报错。
     *
     */
    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) {
        //打印请求开始日志
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String url = request.getRequestURI();

        Object[] objects = joinPoint.getArgs();

        try {
            log.debug("【Aop2】收到请求：{}，开始时间：{}， 参数：{}", url, startTime.get(), JSON.toJSONString(objects));
        } catch (Exception e) {
            log.debug("【Aop2】收到请求：{}，开始时间：{}， 参数：{}", url, startTime.get(), "参数无法序列化");
        }

        /*Class className = joinPoint.getTarget().getClass();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();*/
    }

    /**
     * 方法返回之前执行，打印才返回值以及方法消耗时间
     * @param response 返回值
     *
     * 在目标方法执行之后执行的通知。
     * 在后置通知中也可以选择性的接收一个JoinPoint来获取连接点的额外信息，但是这个参数必须处在参数列表的第一个。
     * 在后置通知中，还可以通过配置returning获取返回值
     */
    @AfterReturning(returning = "response",pointcut = "requestLog()")
    public void doAfterRunning(JoinPoint joinPoint,Object response) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String url = request.getRequestURI();

        long endTime = System.currentTimeMillis();
        log.debug("【Aop2】收到请求：{}，得到结果：{}，结束时间：{}，耗时：{}", url, response == null ?
                "空（NULL）" : JSON.toJSONString(response),endTime,endTime-startTime.get());
    }
}