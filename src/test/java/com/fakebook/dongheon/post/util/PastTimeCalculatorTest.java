package com.fakebook.dongheon.post.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class PastTimeCalculatorTest {
	@Test
	void 지나간_시간_계산기능_동작_확인() {
		//given
		LocalDateTime createTime = LocalDateTime.now();
		createTime = createTime.minusMinutes(70);

		//when
		String pastTime = PastTimeCalculator.getPastTime(createTime);
		assertThat(pastTime).isEqualTo("1시간");
	}

	@Test
	void 일주일_이상_된_게시글_지나간_시간_확인() {
		//given
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime createTime = now.minusDays(8);

		//when
		String pastTime = PastTimeCalculator.getPastTime(createTime);

		//then
		assertThat(pastTime)
				.isEqualTo(createTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
	}
}