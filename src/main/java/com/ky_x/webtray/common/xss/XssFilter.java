package com.ky_x.webtray.common.xss;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;


@WebFilter("/*")
public class XssFilter implements Filter    {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        XssHttpServletRequestWrapper xss = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xss,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
