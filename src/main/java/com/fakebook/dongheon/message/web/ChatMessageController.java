package com.fakebook.dongheon.message.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {
	@MessageMapping("/hello")
	@SendTo("/topic/public-message")
	public ChatMessageRequestDto greeting(ChatMessageRequestDto message) throws Exception {
		return message;
	}
}
