package com.fakebook.dongheon.chatting.domain.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MessageRepositoryCustom {
	private final MessageRepository messageRepository;

	public void save(Message message) {
		messageRepository.save(message);
	}
}
