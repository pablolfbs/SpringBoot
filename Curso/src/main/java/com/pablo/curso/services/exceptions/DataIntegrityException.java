package com.pablo.curso.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 8164086375498573588L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
