package com.fakebook.dongheon.member.domain;

import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import com.fakebook.dongheon.member.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomMemberRepository {
	public final MemberRepository memberRepository;

	public Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId)
				.orElseThrow(MemberNotFoundException::new);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(MemberNotFoundException::new);
	}

	public MemberResponseDto findMemberById(Long id) {
		return memberRepository.findById(id)
				.map(MemberResponseDto::of)
				.orElseThrow(MemberNotFoundException::new);
	}

	public MemberResponseDto findMemberByUserId(String userId) {
		return memberRepository.findByUserId(userId)
				.map(MemberResponseDto::of)
				.orElseThrow(MemberNotFoundException::new);
	}

	public void delete(Member member) {
		memberRepository.delete(member);
	}

	public void deleteAll() {
		memberRepository.deleteAll();
	}

	public boolean isExistUserId(String userId) {
		return memberRepository.findAll().stream()
				.anyMatch(member -> member.getUserId().equals(userId));
	}

	public Member save(Member member) {
		return memberRepository.save(member);
	}
}
