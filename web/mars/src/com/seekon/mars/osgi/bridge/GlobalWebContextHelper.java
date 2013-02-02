package com.seekon.mars.osgi.bridge;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalWebContextHelper {

	private static ServletContext t_servletConext;

	private static final ThreadLocal currentRequest = new ThreadLocal();

	private static final ThreadLocal currentResponse = new ThreadLocal();

	private static Map globalSessionMap = new HashMap();

	public static void setGlobalServletContext(ServletContext sc) {
		t_servletConext = sc;
	}

	public static ServletContext getGlobalServletContext() {
		return t_servletConext;
	}

	public static void setCurrentRequestAndResponse(HttpServletRequest request,
			HttpServletResponse response) {
		currentRequest.set(request);
		currentResponse.set(response);
	}

	public static HttpServletRequest getRequest() {
		return ((HttpServletRequest) currentRequest.get());
	}

	public static HttpServletResponse getResponse() {
		return ((HttpServletResponse) currentResponse.get());
	}

	public static HttpSession getSession(String sessionId) {
		return ((HttpSession) globalSessionMap.get(sessionId));
	}

	public static void putSession(String sessionId, HttpSession session) {
		globalSessionMap.put(sessionId, session);
	}

	public static HttpSession removeSession(String sessionId) {
		return ((HttpSession) globalSessionMap.remove(sessionId));
	}

}
