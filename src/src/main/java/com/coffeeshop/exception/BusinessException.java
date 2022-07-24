package com.coffeeshop.exception;

public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 93409071336264760L;
	private ErrorCode error;
	
	public ErrorCode getError() {
		return this.error;
	}
	public BusinessException(ErrorCode error) {
		
		this.error = error;
	}
	public BusinessException() {}
	  public BusinessException(Throwable cause) {
	        super(cause);
	    }
	  
	 
}
