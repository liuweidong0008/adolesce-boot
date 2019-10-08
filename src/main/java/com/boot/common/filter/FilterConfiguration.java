package com.boot.common.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义 Filter
 *      我们常常在项目中会使用 filters 用于录调用日志、排除有 XSS 威胁的字符、执行权限验证等等。Spring Boot 自动添加了
 *                       OrderedCharacterEncodingFilter 和 HiddenHttpMethodFilter，并且我们可以自定义 Filter。
 *      两个步骤：
 *      1、实现 Filter 接口，实现 Filter 方法
 *      2、添加@Configuration 注解，将自定义Filter加入过滤链*/

@Configuration
public class FilterConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

	@Bean
	public FilterRegistrationBean myFilterOneRegist() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new MyFilterOne());
		registration.addUrlPatterns(new String[] { "/*" });
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("MyFilterOne");
        //默认值Integer.MAX_VALUE 过滤器链按照该值从小到大依次执行
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean myFilterTwoRegist() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new MyFilterTwo());
		registration.addUrlPatterns(new String[] { "/hello" });
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("MyFilterTwo");
		registration.setOrder(2);
		return registration;
	}

	public class MyFilterOne implements Filter {
		public MyFilterOne() {
		}

	  	@Override
        public void destroy() {
            // TODO Auto-generated method stub
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            // TODO Auto-generated method stub
            HttpServletRequest request = (HttpServletRequest) srequest;
            System.out.println("this is MyFilterOne,url :"+request.getRequestURI());
            filterChain.doFilter(srequest, sresponse);
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            // TODO Auto-generated method stub
        }
    }
    
    public class MyFilterTwo implements Filter {
        @Override
        public void destroy() {
            // TODO Auto-generated method stub
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            // TODO Auto-generated method stub
            HttpServletRequest request = (HttpServletRequest) srequest;
            System.out.println("this is MyFilterTwo,url :"+request.getRequestURI());
            filterChain.doFilter(srequest, sresponse);
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            // TODO Auto-generated method stub
        }
    }
}