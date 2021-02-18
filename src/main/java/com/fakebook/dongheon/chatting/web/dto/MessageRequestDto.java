package com.fakebook.dongheon.chatting.web.dto;

import lombok.Getter;

@Getter
public class MessageRequestDto {
	private Long senderId;
	private Long receiverId;
	private String content;
}
