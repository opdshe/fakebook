package com.fakebook.dongheon.member.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUserId(String userId);

	@EntityGraph(attributePaths = {"friends"})
	Optional<Member> findWithFriendsById(Long id);
}
