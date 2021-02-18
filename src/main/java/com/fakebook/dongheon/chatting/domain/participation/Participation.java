package com.fakebook.dongheon.chatting.domain.participation;

import com.fakebook.dongheon.chatting.domain.chatroom.ChatRoom;
import com.fakebook.dongheon.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Participation {
	@Column(name = "participation_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chatroom_id")
	private ChatRoom chatRoom;

	public Participation(Member member, ChatRoom chatRoom) {
		this.member = member;
		this.chatRoom = chatRoom;
	}
}
