package com.fakebook.dongheon.comment.exception;

public class CommentNotFoundException extends RuntimeException {
	private static final String message = "해당 댓글을 찾을 수 없습니다. ";

	public CommentNotFoundException() {
		super(message);
	}
}
