package com.fakebook.dongheon.profile.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/profile")
@Controller
public class ProfileController {
	@GetMapping("")
	public String profile() {
		return "profile";
	}
}
