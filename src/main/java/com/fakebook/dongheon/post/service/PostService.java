package com.fakebook.dongheon.post.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
	private final CustomPostRepository customPostRepository;
	private final CustomMemberRepository customMemberRepository;

	@Transactional
	public Long register(PostRegisterDto dto, String loginUserId) {
		Member member = customMemberRepository.findByUserId(loginUserId);
		return customPostRepository.save(dto.toEntity(member)).getId();
	}
}
