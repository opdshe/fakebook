package com.fakebook.dongheon.chatting.web.dto;

import com.fakebook.dongheon.chatting.domain.message.Message;
import lombok.Getter;

@Getter
public class MessageResponseDto {
	private final Long senderId;
	private final Long receiverId;
	private final String content;

	public MessageResponseDto(Message message) {
		this.senderId = message.getSender().getId();
		this.receiverId = message.getReceiver().getId();
		this.content = message.getContent();
	}
}
