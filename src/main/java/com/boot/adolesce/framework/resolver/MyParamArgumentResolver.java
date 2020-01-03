/*******************************************************************************
 * @(#)CurrentUserContextArgumentResolver.java 2019年08月29日 23:10
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.framework.resolver;

import com.boot.adolesce.framework.properties.PersonProperties;
import com.boot.adolesce.module.commonbean.MyParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <b>Application name：</b> TerminalInfoArgumentResolver.java <br>
 * <b>Application describing： </b> 参数信息设置解析器 <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年08月29日 23:10 <br>
 * <b>@author：</b> <a href="mailto:chensg@miyzh.com"> chensg </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Configuration
public class MyParamArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private PersonProperties personProperties;

    /**
     * 用于判定是否需要处理该参数，返回true为需要，并会去调用下面的方法resolveArgument。
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(MyParam.class)) {
            return true;
        }
        return false;

        /*Class<?> clazz = methodParameter.getParameterType();//如若不是MyParam类不进行接下来操作
        return clazz == MyParam.class;*/

    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        MyParam myParam = new MyParam();
        myParam.setName(personProperties.getName());
        myParam.setAge(personProperties.getAge());
        myParam.setAddress(personProperties.getAddress());
        return myParam;
    }

}