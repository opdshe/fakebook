package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
	private final CustomMemberRepository memberRepository;

	public MemberService(CustomMemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public Long register(MemberRegisterDto dto) {
		return memberRepository.save(dto.toEntity()).getId();
	}
}
