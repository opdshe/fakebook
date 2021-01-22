package com.fakebook.dongheon.member.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.exception.AlreadyExistMemberIdException;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import com.fakebook.dongheon.member.web.dto.MemberResponseDto;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Transactional
	public void delete(Long id) {
		validateAuthority(id);
		Member member = memberRepository.findById(id);
		memberRepository.delete(member);
	}

	public MemberResponseDto getMemberById(Long id) {
		return memberRepository.findMemberById(id);
	}

	private void validateAuthority(Long id) {
		UserDetails loginUser = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
		String loginUserId = loginUser.getUsername();
		Member targetMember = memberRepository.findById(id);
		if (!loginUserId.equals(targetMember.getUserId())) {
			throw new NotAuthorizedException();
		}
	}

	private void validateDuplicatedId(String userId) {
		if (memberRepository.isExistUserId(userId)) {
			throw new AlreadyExistMemberIdException();
		}
	}
}
