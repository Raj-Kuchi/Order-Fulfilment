package com.zl.fulfilment.exceptions;

import org.springframework.http.HttpStatus;

public class CatalogInitializedException {

	private HttpStatus status;
	
	private String message;
	
	public CatalogInitializedException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
