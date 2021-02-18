package com.fakebook.dongheon.chatting.service;

import com.fakebook.dongheon.chatting.domain.chatroom.ChatRoom;
import com.fakebook.dongheon.chatting.domain.chatroom.ChatRoomRepositoryCustom;
import com.fakebook.dongheon.chatting.domain.participation.Participation;
import com.fakebook.dongheon.chatting.domain.participation.ParticipationRepositoryCustom;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
	private final MemberRepositoryCustom memberRepository;
	private final ParticipationRepositoryCustom participationRepository;
	private final ChatRoomRepositoryCustom chatRoomRepository;

	public ChatRoom getChatRoom(Member sender, Member receiver) {
		List<Participation> senderParticipants = participationRepository.getParticipationList(sender);
		List<Participation> receiverParticipants = participationRepository.getParticipationList(receiver);
		return findChatRoom(senderParticipants, receiverParticipants);
	}

	public void createChatRoom(String loginUserId, Long receiverId) {
		Member sender = memberRepository.findByUserId(loginUserId);
		Member receiver = memberRepository.findById(receiverId);
		List<Participation> senderParticipants = participationRepository.getParticipationList(sender);
		List<Participation> receiverParticipants = participationRepository.getParticipationList(receiver);
		ChatRoom chatRoom = findChatRoom(senderParticipants, receiverParticipants);
		if (Objects.isNull(chatRoom)) {
			chatRoom = chatRoomRepository.save(ChatRoom.empty());
			participationRepository.save(new Participation(sender, chatRoom));
			participationRepository.save(new Participation(receiver, chatRoom));
		}
	}

	private ChatRoom findChatRoom(List<Participation> senderParticipants,
								  List<Participation> receiverParticipants) {
		List<ChatRoom> senderChatRooms = senderParticipants.stream()
				.map(Participation::getChatRoom)
				.collect(Collectors.toList());
		List<ChatRoom> receiverChatRoom = receiverParticipants.stream()
				.map(Participation::getChatRoom)
				.collect(Collectors.toList());
		return senderChatRooms.stream()
				.filter(receiverChatRoom::contains)
				.findFirst()
				.orElse(null);
	}
}
