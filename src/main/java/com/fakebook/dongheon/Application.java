package com.fakebook.dongheon;

import com.fakebook.dongheon.member.domain.Gender;
import com.fakebook.dongheon.member.domain.Member;
import com.fakebook.dongheon.member.domain.MemberRepositoryCustom;
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
	private final MemberRepositoryCustom memberRepositoryCustom;
	private final CustomPostRepository customPostRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//샘플 데이터 저장
		Member dongheon = Member.builder()
				.userId("test")
				.password(new BCryptPasswordEncoder().encode("1234"))
				.birthday(LocalDate.of(1995, 8, 22))
				.name("이동헌 (운영자)")
				.gender(Gender.MALE)
				.build();
		Member minsub = Member.builder()
				.userId("aaa")
				.password(new BCryptPasswordEncoder().encode("aaa"))
				.birthday(LocalDate.of(1995, 8, 22))
				.name("김민섭")
				.gender(Gender.MALE)
				.build();
		memberRepositoryCustom.save(dongheon);
		memberRepositoryCustom.save(minsub);

		String youtubeUrl = "https://www.youtube.com/embed/XAwEz-_sg9k";
		Post samplePost = new Post("공부할 땐 역시 로파이지~👍🏻", dongheon, youtubeUrl);
		customPostRepository.save(samplePost);
	}
}
