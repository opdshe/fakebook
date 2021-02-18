package com.fakebook.dongheon.chatting.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepositoryCustom {
	private final ChatRoomRepository chatRoomRepository;

	public ChatRoom save(ChatRoom chatRoom) {
		return chatRoomRepository.save(chatRoom);
	}
}
