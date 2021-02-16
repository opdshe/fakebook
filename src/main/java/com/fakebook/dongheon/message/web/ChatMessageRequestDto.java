package com.fakebook.dongheon.message.web;

import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
	private Long sender;
	private Long receiver;
	private String content;
}
