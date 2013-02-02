package com.seekon.mars.osgi.http.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seekon.mars.osgi.bridge.GlobalWebContextHelper;

public class RequestWrappingFilter implements Filter {

	private Set unCheckSessionUrlSet = new HashSet();

	public void destroy() {

	}

	/**
	 * 检测session是否失效 对request进行封装，以便session的处理
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			if (request instanceof HttpServletRequest) {
				HttpServletRequest requestObject = (HttpServletRequest) request;
				HttpServletResponse responseObject = (HttpServletResponse) response;

				if (!validateSession(requestObject)) {
					printSessionError(requestObject, responseObject);
					return;
				}

				HttpServletRequest newRequest = new HttpRequestWrapper(requestObject);
				HttpServletResponse newResponse = new HttpResponseWrapper(
						responseObject);
				GlobalWebContextHelper.setCurrentRequestAndResponse(newRequest,
						newResponse);
				HttpSession session = newRequest.getSession(false);
				if (session != null) {
					GlobalWebContextHelper.putSession(session.getId(), session);
				}

				try {
					chain.doFilter(newRequest, newResponse);
					return;
				} finally {
					doFinal((HttpRequestWrapper) newRequest,
							(HttpResponseWrapper) newResponse);
				}
			}
			chain.doFilter(request, response);
		} finally {
			GlobalWebContextHelper.setCurrentRequestAndResponse(null, null);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		String unCheckSessionUrls = config.getInitParameter("unCheckSessionUrls");
		if (unCheckSessionUrls != null) {
			unCheckSessionUrlSet.addAll(Arrays.asList(unCheckSessionUrls.split(",")));
		}
	}

	/**
	 * 检测session是否失效，还得改进与其他war共用时session复制的问题
	 * 
	 * @param request
	 * @return
	 */
	private boolean validateSession(HttpServletRequest request) {
		return true;
	}

	/**
	 * session失效的输出
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalArgumentException
	 */
	public static void printSessionError(HttpServletRequest request,
			HttpServletResponse response) throws IllegalArgumentException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			throw new IllegalArgumentException("Dispatcher类返回结果到前端时IO错。");
		}
		String indexUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();
		out.println("<html>");
		out.println("<body><table><tr><td>");
		out.println("会话停顿时间超限，当前操作失败。请<a href=\"" + indexUrl + "\">重新登录</a>！<br>");

		out.println("</td></tr></table></body>");
		out.println("</html>");
	}

	/**
	 * 过滤器处理后finally调用的收尾工作
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doFinal(HttpRequestWrapper request, HttpResponseWrapper response)
			throws ServletException, IOException {
		// TODO
	}
}
