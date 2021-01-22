package com.fakebook.dongheon;

import com.fakebook.dongheon.member.domain.CustomMemberRepository;
import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.post.domain.CustomPostRepository;
import com.fakebook.dongheon.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
	private final CustomMemberRepository customMemberRepository;
	private final CustomPostRepository customPostRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Member sampleMember = Member.builder()
				.userId("test")
				.password(new BCryptPasswordEncoder().encode("1234"))
				.birthday(LocalDate.of(1995, 8, 22))
				.name("이동헌")
				.gender(Gender.MALE)
				.build();
		customMemberRepository.save(sampleMember);

		String youtubeUrl = "https://www.youtube.com/embed/1yXZIFYv4SE";
		Post samplePost = new Post("요즘 공부할 때 듣는 노래", sampleMember, youtubeUrl);
		customPostRepository.save(samplePost);
	}
}
