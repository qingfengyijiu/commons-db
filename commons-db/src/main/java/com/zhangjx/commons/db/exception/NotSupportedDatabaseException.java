package com.zhangjx.commons.db.exception;

public class NotSupportedDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 7875262390716560144L;
	

	public NotSupportedDatabaseException() {
		super();
	}
	
	public NotSupportedDatabaseException(String message) {
		super(message);
	}
	
	public NotSupportedDatabaseException(String message, Throwable t) {
		super(message, t);
	}
	
	public NotSupportedDatabaseException(Throwable t) {
		super(t);
	}
	
}
