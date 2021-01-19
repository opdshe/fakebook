package com.fakebook.dongheon;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.web.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
	private final CustomMemberRepository customMemberRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MemberRegisterDto dto = new MemberRegisterDto("test", "1234", "이동헌",
				1995, 8, 22, Gender.MALE);
		customMemberRepository.save(dto.toEntity());
	}
}
