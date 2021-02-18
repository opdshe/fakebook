package com.fakebook.dongheon.chatting.domain.participation;

import com.fakebook.dongheon.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ParticipationRepositoryCustom {
	private final ParticipationRepository participationRepository;

	public List<Participation> getParticipationList(Member participator) {
		return participationRepository.findAllByMember(participator);
	}

	public void save(Participation participation) {
		participationRepository.save(participation);
	}
}
