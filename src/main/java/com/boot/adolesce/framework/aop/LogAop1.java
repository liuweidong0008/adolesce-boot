/*******************************************************************************
 * @(#)LogAop.java 2019年11月01日 15:58
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.framework.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <b>Application name：</b> LogAop.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年11月01日 15:58 <br>
 * <b>@author：</b> <a href="mailto:syx@miyzh.com"> shiyx </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Aspect
@Component
@Slf4j
public class LogAop1 {
    //在目标方法执行之前和之后都可以执行额外代码的通知。
    //在环绕通知中必须显式的调用目标方法，目标方法才会执行，这个显式调用时通过ProceedingJoinPoint来实现的，可以在环绕通知中接收一个此类型的形参，spring容器会自动将该对象传入，注意这个参数必须处在环绕通知的第一个形参位置。
    //注意，只有环绕通知可以接收ProceedingJoinPoint，而其他通知只能接收JoinPoint。
    //环绕通知需要返回返回值，否则真正调用者将拿不到返回值，只能得到一个null。
    //环绕通知有控制目标方法是否执行、有控制是否返回值、有改变返回值的能力。
    //环绕通知虽然有这样的能力，但一定要慎用，不是技术上不可行，而是要小心不要破坏了软件分层的“高内聚 低耦合”的目标。
    @Around("@annotation(requestMapping))")
    public Object around(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) {
        return commonAround(joinPoint);
    }

    @Around("@annotation(getMapping))")
    public Object around2(ProceedingJoinPoint joinPoint, GetMapping getMapping) {
        return commonAround(joinPoint);
    }

    private Object commonAround(ProceedingJoinPoint joinPoint) {
        //打印请求开始日志
        Date start = new Date();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();

        Object[] objects = joinPoint.getArgs();

        try {
            log.debug("【Aop1】收到请求：{}，开始时间：{}， 参数：{}", url, start, JSON.toJSONString(objects));
        } catch (Exception e) {
            log.debug("【Aop1】收到请求：{}，开始时间：{}， 参数：{}", url, start, "参数无法序列化");
        }

        /*Class className = joinPoint.getTarget().getClass();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();*/

        //调用方法
        try {
            Object object = joinPoint.proceed(objects);
            Date end = new Date();
            long step = end.getTime() - start.getTime();
            log.debug("【Aop1】收到请求：{}，得到结果：{}，结束时间：{}，耗时：{}", url, object == null ?
                            "空（NULL）" : JSON.toJSONString(object),
                    end, step);
            /*if (object instanceof RetData) {
                RetData result = (RetData) object;
                return result;
            }*/
            return object;
        } catch (Throwable e) {
            e.printStackTrace();
            Date end = new Date();
            long step = end.getTime() - start.getTime();
            log.error("【Aop1】收到请求：{}，发生错误，结束时间：{}，耗时：{}。", url, end,
                    step, e);
            return null;
            //return RetData.fail("未知异常，请稍后重试或联系管理员");
        }
    }
}