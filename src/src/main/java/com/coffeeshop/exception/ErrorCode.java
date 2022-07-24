package com.coffeeshop.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	ERROR_INVALID_SHOP_CODE(HttpStatus.BAD_REQUEST, "Invalid shop code"),
	ERROR_NO_ORDER_FOUND(HttpStatus.NOT_FOUND, "Order not found");

	private HttpStatus code;
	private String description;

	public HttpStatus getHttpStatus() {
		
		return this.code;
		
	}
	public String getDesc() {
		return description;
	}
	ErrorCode(HttpStatus code, String description) {
		this.code = code;
		this.description = description;
	}
}
