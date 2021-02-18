package com.fakebook.dongheon.post.service;

import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
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
	private final MemberRepositoryCustom memberRepositoryCustom;

	@Transactional
	public Long register(PostRegisterDto dto, String loginUserId) {
		Member member = memberRepositoryCustom.findByUserId(loginUserId);
		return customPostRepository.save(dto.toEntity(member)).getId();
	}

	@Transactional
	public void delete(Long id, String loginUserId) {
		Member loginUser = memberRepositoryCustom.findByUserId(loginUserId);
		Post post = customPostRepository.findById(id);
		validateAuthority(post, loginUser);
		customPostRepository.delete(post);
	}

	@Transactional
	public void update(Long id, PostRegisterDto dto, String loginUserId) {
		Member loginUser = memberRepositoryCustom.findByUserId(loginUserId);
		Post post = customPostRepository.findById(id);
		validateAuthority(post, loginUser);
		post.update(dto);
	}

	@Transactional
	public List<PostResponseDto> getFeedPosts(String loginUserId) {
		Member loginUser = memberRepositoryCustom.findByUserId(loginUserId);
		return customPostRepository.findAll().stream()
				.sorted(Comparator.comparing(Post::getPostDateTime).reversed())
				.map(post -> PostResponseDto.of(post, loginUser))
				.collect(Collectors.toList());
	}

	@Transactional
	public List<PostResponseDto> getProfilePosts(Long memberId, String loginUserId) {
		Member loginUser = memberRepositoryCustom.findByUserId(loginUserId);
		Member member = memberRepositoryCustom.findById(memberId);
		return customPostRepository.findAllByMember(member).stream()
				.sorted(Comparator.comparing(Post::getPostDateTime).reversed())
				.map(post -> PostResponseDto.of(post, loginUser))
				.collect(Collectors.toList());
	}

	@Transactional
	public Integer like(Long postId, String loginUserId) {
		Member member = memberRepositoryCustom.findByUserId(loginUserId);
		Post post = customPostRepository.findById(postId);
		return member.likePost(post);
	}

	private static void validateAuthority(Post post, Member loginUser) {
		if (post.getMember() != loginUser) {
			throw new NotAuthorizedException();
		}
	}
}
