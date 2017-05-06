package com.myapp.core.uuid;

public class UuidException extends RuntimeException {
	public UuidException() {
	}

	public UuidException(String message, Throwable cause) {
		super(message, cause);
	}

	public UuidException(Throwable cause) {
		super(cause);
	}

	public UuidException(String msg) {
		super(msg);
	}
}
