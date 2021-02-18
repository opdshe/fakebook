package com.fakebook.dongheon.chatting.web;

import com.fakebook.dongheon.chatting.service.ChatRoomService;
import com.fakebook.dongheon.chatting.service.ChatService;
import com.fakebook.dongheon.chatting.web.dto.MessageRequestDto;
import com.fakebook.dongheon.chatting.web.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {
	private static final String ONE_TO_ONE_MESSAGE_PREFIX = "/queue/";

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ChatRoomService chatRoomService;
	private final ChatService chatService;

	@MessageMapping("/send")
	public void send(MessageRequestDto message) {
		String destination = ONE_TO_ONE_MESSAGE_PREFIX + message.getReceiverId();
		chatService.save(message);
		simpMessagingTemplate.convertAndSend(destination, message);
	}

	@PostMapping("/create-chatroom/{receiverId}")
	public void createChatRoom(@PathVariable("receiverId") Long receiverId, Principal principal) {
		String loginUserId = principal.getName();
		chatRoomService.createChatRoom(loginUserId, receiverId);
	}

	@GetMapping("/fetch-messages/{receiverId}")
	public List<MessageResponseDto> fetchMessages(@PathVariable("receiverId") Long receiverId, Principal principal) {
		String loginUserId = principal.getName();
		return chatService.getMessages(loginUserId, receiverId);
	}
}
