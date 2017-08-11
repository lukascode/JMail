package com.lukascode.jmail.common;

public class JMailException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JMailException(String msg) {
		super(msg);
	}
	
	public JMailException(String msg, Throwable x) {
		super(msg, x);
	}
}
