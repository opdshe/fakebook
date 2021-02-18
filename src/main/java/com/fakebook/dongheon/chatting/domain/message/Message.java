package com.fakebook.dongheon.chatting.domain.message;

import com.fakebook.dongheon.JpaBaseEntity;
import com.fakebook.dongheon.chatting.domain.chatroom.ChatRoom;
import com.fakebook.dongheon.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Message extends JpaBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Member sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private Member receiver;

	@Column
	private String content;

	@ManyToOne
	@JoinColumn(name = "chatroom_id")
	private ChatRoom chatRoom;

	public Message(Member sender, Member receiver, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		chatRoom.getMessages().add(this);
	}
}
