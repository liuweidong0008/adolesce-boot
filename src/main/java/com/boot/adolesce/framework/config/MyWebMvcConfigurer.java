/*******************************************************************************
 * @(#)WebMvcConfigurerAdapter.java 2019年11月09日 15:44
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.framework.config;

import com.boot.adolesce.framework.converter.DateConverter;
import com.boot.adolesce.framework.interceptor.MyInterceptor;
import com.boot.adolesce.framework.resolver.MyArgumentTrimResolver;
import com.boot.adolesce.framework.resolver.MyParamArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>Application name：</b> WebMvcConfigurerAdapter.java <br>
 * <b>Application describing： </b> WebMvc配置 <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年11月09日 15:44 <br>
 * <b>@author：</b> <a href="mailto:syx@miyzh.com"> shiyx </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private MyParamArgumentResolver myParamArgumentResolver;
    @Resource
    private MyArgumentTrimResolver myArgumentTrimResolver;
    @Resource
    private MyInterceptor myInterceptor;

    /**
     * 添加参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(myParamArgumentResolver);
        argumentResolvers.add(myArgumentTrimResolver);
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/abc","/configurePathMatch","/**");
    }

    /**
     * 添加日期解析器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
     }

}