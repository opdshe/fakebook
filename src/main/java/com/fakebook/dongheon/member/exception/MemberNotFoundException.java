package com.fakebook.dongheon.member.exception;

public class MemberNotFoundException extends RuntimeException {
	private static final String message = "해당 회원을 찾을 수 없습니다. ";

	public MemberNotFoundException() {
		super(message);
	}
}
