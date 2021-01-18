package com.fakebook.dongheon.security.web;

import com.fakebook.dongheon.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserDetailsController {
	private final CustomUserDetailsService customUserDetailsService;

	@GetMapping("/user-name")
	public String loginUserName(Principal principal) {
		String loginUserId = principal.getName();
		return customUserDetailsService.getLoginUserName(loginUserId);
	}
}
