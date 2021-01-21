package com.fakebook.dongheon.feed.web;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.security.SecurityConfig;
import com.fakebook.dongheon.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = FeedController.class)
class FeedControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomMemberRepository customMemberRepository;

	@MockBean
	private CustomPostRepository customPostRepository;

	@MockBean
	private CustomUserDetailsService userDetailsService;

	@Test
	void Feed_페이지_호출_동작_확인() throws Exception {
		mockMvc.perform(get("/feed"))
				.andExpect(status().isOk())
				.andDo(print());
	}
}