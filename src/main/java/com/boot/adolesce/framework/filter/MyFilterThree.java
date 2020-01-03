package com.boot.adolesce.framework.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = { "/*" }, filterName = "myFilterThree")
public class MyFilterThree implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
    }


    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) srequest;
        System.out.println("this is MyFilterThree,url :"+request.getRequestURI());
        filterChain.doFilter(srequest, sresponse);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
}