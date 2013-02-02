package com.seekon.smartclient.framework.exception;

public class MenuNotFoundException extends Exception {
	private static final long serialVersionUID = -5428076216991010445L;

	public MenuNotFoundException() {
		super();
	}

	public MenuNotFoundException(String message) {
		super(message);
	}

	public MenuNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuNotFoundException(Throwable cause) {
		super(cause);
	}
}
