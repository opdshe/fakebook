package com.fakebook.dongheon.security;

import com.fakebook.dongheon.member.domain.Member;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class SecurityMember extends User {

	public SecurityMember(Member member) {
		super(member.getUserId(), member.getPassword(), new ArrayList<>());
	}
}
