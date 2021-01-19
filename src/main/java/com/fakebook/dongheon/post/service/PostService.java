package com.fakebook.dongheon.post.service;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import com.fakebook.dongheon.post.web.dto.PostRegisterDto;
import com.fakebook.dongheon.post.web.dto.PostResponseDto;
import com.fakebook.dongheon.security.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

	@Transactional
	public void delete(Long id, String loginUserId) {
		Post post = customPostRepository.findById(id);
		Member loginUser = customMemberRepository.findByUserId(loginUserId);
		validateAuthority(post, loginUser);
		customPostRepository.delete(post);
	}

	@Transactional
	public void update(Long id, PostRegisterDto dto, String loginUserId) {
		Post post = customPostRepository.findById(id);
		Member loginUser = customMemberRepository.findByUserId(loginUserId);
		validateAuthority(post, loginUser);
		post.update(dto);
	}

	@Transactional
	public List<PostResponseDto> findAllOrderByPostDate() {
		return customPostRepository.findAll().stream()
				.sorted(Comparator.comparing(Post::getPostDate).reversed())
				.map(PostResponseDto::of)
				.collect(Collectors.toList());
	}

	private static void validateAuthority(Post post, Member loginUser) {
		if (post.getMember() != loginUser) {
			throw new NotAuthorizedException();
		}
	}
}
