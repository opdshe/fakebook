package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.exception.AlreadyExistMemberIdException;
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
		validateDuplicatedId(dto.getUserId());
		return memberRepository.save(dto.toEntity()).getId();
	}

	@Transactional
	public void update(Long id, MemberRegisterDto dto) {
		validateDuplicatedId(dto.getUserId());
		Member member = memberRepository.findById(id);
		member.update(dto);
	}

	private void validateDuplicatedId(String userId) {
		if (memberRepository.isAlreadyExistUserId(userId)) {
			throw new AlreadyExistMemberIdException();
		}
	}
}
