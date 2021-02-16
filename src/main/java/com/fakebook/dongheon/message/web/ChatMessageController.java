package com.fakebook.dongheon.message.web;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {
	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/send")
	public void send(ChatMessageRequestDto message) {
		String destination = "/queue/" + message.getReceiver();
		System.out.println(destination);
		simpMessagingTemplate.convertAndSend(destination, message);
	}
}
