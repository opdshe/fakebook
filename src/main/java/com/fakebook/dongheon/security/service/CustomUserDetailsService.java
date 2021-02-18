package com.fakebook.dongheon.security.service;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
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
	private final MemberRepositoryCustom memberRepositoryCustom;

	public MemberResponseDto getLoginUser(String userId) {
		return memberRepositoryCustom.findMemberByUserId(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member = memberRepositoryCustom.findByUserId(userId);
		return new SecurityMember(member);
	}
}
