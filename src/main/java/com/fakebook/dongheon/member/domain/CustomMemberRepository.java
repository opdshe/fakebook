package com.fakebook.dongheon.member.domain;

import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMemberRepository {
	public final MemberRepository memberRepository;

	public CustomMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId)
				.orElseThrow(MemberNotFoundException::new);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);
	}

	public void deleteAll() {
		memberRepository.deleteAll();
	}

	public boolean isAlreadyExistUserId(String userId) {
		return memberRepository.findAll().stream()
				.anyMatch(member -> member.getUserId().equals(userId));
	}

	public Member save(Member member) {
		return memberRepository.save(member);
	}
}
