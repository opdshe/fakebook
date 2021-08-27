package com.fakebook.dongheon.security.service;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepository;
import com.fakebook.dongheon.member.exception.MemberNotFoundException;
import com.fakebook.dongheon.member.web.dto.MemberResponseDto;
import com.fakebook.dongheon.security.SecurityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	public MemberResponseDto getLoginUser(String userId) {
		return memberRepository.findByUserId(userId)
				.map(MemberResponseDto::of)
				.orElseThrow(MemberNotFoundException::new);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member = memberRepository.findByUserId(userId)
				.orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다. "));
		return new SecurityMember(member);
	}
}
