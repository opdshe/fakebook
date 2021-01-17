package com.fakebook.dongheon.security.exception;

public class NotAuthorizedException extends RuntimeException {
	private static final String message = "잘못된 접근입니다. ";

	public NotAuthorizedException() {
		super(message);
	}
}
