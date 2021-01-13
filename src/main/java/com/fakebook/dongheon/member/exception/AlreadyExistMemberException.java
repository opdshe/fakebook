package com.fakebook.dongheon.member.exception;

public class AlreadyExistMemberException extends RuntimeException {
	private static String message = "이미 존재하는 ID 입니다. ";

	public AlreadyExistMemberException() {
		super(message);
	}
}
