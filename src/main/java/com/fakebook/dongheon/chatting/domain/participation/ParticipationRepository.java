package com.fakebook.dongheon.chatting.domain.participation;

import com.fakebook.dongheon.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
	List<Participation> findAllByMember(Member member);
}
