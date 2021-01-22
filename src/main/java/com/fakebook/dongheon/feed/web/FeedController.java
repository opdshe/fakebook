package com.fakebook.dongheon.feed.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {
	@GetMapping("/feed")
	public String feed() {
		return "feed";
	}
}
