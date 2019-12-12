/*******************************************************************************
 * @(#)CurrentUserContextArgumentResolver.java 2019年08月29日 23:10
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.resolver;

import com.boot.common.annotation.RequestParamsTrim;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <b>Application name：</b> TerminalInfoArgumentResolver.java <br>
 * <b>Application describing： </b> 参数trim解析器 <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年08月29日 23:10 <br>
 * <b>@author：</b> <a href="mailto:chensg@miyzh.com"> chensg </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Configuration
public class MyArgumentTrimResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判定是否需要处理该参数，返回true为需要，并会去调用下面的方法resolveArgument。
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestParamsTrim.class);
    }

    /**
     * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象。
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        //参数类型(com.boot.users.entity.Users)
        Class clazz = methodParameter.getParameterType();

        //Map类型
        if (Map.class.isAssignableFrom(clazz)) {
            Map<String, String> resultMap = new HashMap<>();
            //遍历参数Map，trim后返回
            for(Map.Entry<String,String[]> entry : nativeWebRequest.getParameterMap().entrySet()){
                resultMap.put(entry.getKey(), entry.getValue()[0].trim());
            }
            return resultMap;
        //类类型
        } else {
            Object resultObject = BeanUtils.instantiateClass(clazz);
            //类中的字段
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            Map<String,Field> fieldMap = Maps.uniqueIndex(fields,e -> e.getName());
            //遍历请求参数
            for(Map.Entry<String,String[]> entry : nativeWebRequest.getParameterMap().entrySet()){
                //设置进对象中
                Field field = fieldMap.get(entry.getKey());
                if(Objects.nonNull(field)){
                    field.setAccessible(true);
                    String value = entry.getValue()[0].trim();
                    if(StringUtils.equals(entry.getKey(),"passWord")){
                        value = "*" +value + "*";
                    }
                    field.set(resultObject,value);
                }
            }
            return resultObject;
        }
    }
}