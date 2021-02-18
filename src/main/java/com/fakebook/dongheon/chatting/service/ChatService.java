package com.fakebook.dongheon.chatting.service;

import com.fakebook.dongheon.chatting.domain.chatroom.ChatRoom;
import com.fakebook.dongheon.chatting.domain.message.Message;
import com.fakebook.dongheon.chatting.domain.message.MessageRepositoryCustom;
import com.fakebook.dongheon.chatting.web.dto.MessageRequestDto;
import com.fakebook.dongheon.chatting.web.dto.MessageResponseDto;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
	private final MemberRepositoryCustom memberRepository;
	private final MessageRepositoryCustom messageRepository;
	private final ChatRoomService chatRoomService;

	@Transactional
	public void save(MessageRequestDto messageRequestDto) {
		Member sender = memberRepository.findById(messageRequestDto.getSenderId());
		Member receiver = memberRepository.findById(messageRequestDto.getReceiverId());
		ChatRoom chatRoom = chatRoomService.getChatRoom(sender, receiver);
		Message message = new Message(sender, receiver, messageRequestDto.getContent());
		message.setChatRoom(chatRoom);
		messageRepository.save(message);
	}

	@Transactional(readOnly = true)
	public List<MessageResponseDto> getMessages(String loginUserId, Long receiverId) {
		Member sender = memberRepository.findByUserId(loginUserId);
		Member receiver = memberRepository.findById(receiverId);
		ChatRoom chatRoom = chatRoomService.getChatRoom(sender, receiver);
		return chatRoom.getMessageResponseDtos();
	}
}
