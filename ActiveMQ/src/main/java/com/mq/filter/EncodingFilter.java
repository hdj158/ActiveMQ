package com.mq.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter{

	private String encoding;
	
	private boolean forceEncoding;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String httpMethod = req.getMethod();//寰楀埌璇锋眰鐨勬彁浜ゆ柟寮�
		if("GET".equals(httpMethod)){
			EncodingHttpServletRequest httpRequest = new EncodingHttpServletRequest(req, encoding);
			chain.doFilter(httpRequest, res);
		}else{
			request.setCharacterEncoding(encoding);
			
			if(this.forceEncoding) {
				response.setCharacterEncoding(encoding);
			}
			
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		encoding = filterConfig.getInitParameter("encoding");
		String force = filterConfig.getInitParameter("forceEncoding");
		forceEncoding = Boolean.parseBoolean(force);
	}

}
