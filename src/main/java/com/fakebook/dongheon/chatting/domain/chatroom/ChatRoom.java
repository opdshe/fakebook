package com.fakebook.dongheon.chatting.domain.chatroom;

import com.fakebook.dongheon.chatting.domain.message.Message;
import com.fakebook.dongheon.chatting.web.dto.MessageResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {
	@Column(name = "chatroom_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
	private List<Message> messages = new ArrayList<>();

	public static ChatRoom empty() {
		return new ChatRoom();
	}

	public List<MessageResponseDto> getMessageResponseDtos() {
		return messages.stream()
				.map(MessageResponseDto::new)
				.collect(Collectors.toList());
	}
}
