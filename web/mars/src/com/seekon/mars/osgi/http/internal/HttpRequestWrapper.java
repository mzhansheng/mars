package com.seekon.mars.osgi.http.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

	public HttpRequestWrapper(HttpServletRequest arg0) {
		super(arg0);
	}

	public HttpSession getSession() {
		HttpSession session = super.getSession();
		if (session == null) {
			return null;
		}
		if (session instanceof HttpSessionWrapper) {
			return session;
		}
		return new HttpSessionWrapper(session);
	}

	public HttpSession getSession(boolean create) {
		HttpSession session = super.getSession(create);
		if (session == null) {
			return null;
		}
		if (session instanceof HttpSessionWrapper) {
			return session;
		}
		return new HttpSessionWrapper(session);
	}

}
