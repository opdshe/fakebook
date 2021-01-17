package com.fakebook.dongheon.post.exception;

public class PostNotFoundException extends RuntimeException {
	private static final String message = "해당 게시글을 찾을 수 없습니다. ";

	public PostNotFoundException() {
		super(message);
	}
}
