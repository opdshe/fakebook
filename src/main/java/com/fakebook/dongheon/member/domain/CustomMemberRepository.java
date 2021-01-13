package com.fakebook.dongheon.member.domain;

import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMemberRepository {
	private final MemberRepository memberRepository;

	public CustomMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member save(Member member) {
		return memberRepository.save(member);
	}

	public Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId)
				.orElseThrow(MemberNotFoundException::new);
	}
}
