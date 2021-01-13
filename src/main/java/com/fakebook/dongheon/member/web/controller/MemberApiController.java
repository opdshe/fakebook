package com.fakebook.dongheon.member.web.controller;

import com.fakebook.dongheon.member.service.MemberService;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberApiController {
	private MemberService memberService;

	@PostMapping("/register")
	public Long register(@RequestBody MemberRegisterDto dto) {
		return memberService.register(dto);
	}
}
