package com.seekon.mars.osgi.http.internal;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpResponseWrapper extends HttpServletResponseWrapper {

	private int httpStatus;

	public HttpResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	public void sendError(int sc) throws IOException {
		httpStatus = sc;
		super.sendError(sc);
	}

	public void sendError(int sc, String msg) throws IOException {
		httpStatus = sc;
		super.sendError(sc, msg);
	}

	public void setStatus(int sc) {
		httpStatus = sc;
		super.setStatus(sc);
	}

	public void setStatus(int sc, String sm) {
		httpStatus = sc;
		super.setStatus(sc, sm);
	}

	public int getHttpStatus() {
		return httpStatus;
	}

}
