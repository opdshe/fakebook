package com.fakebook.dongheon.post.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PastTimeCalculator {
	private static final String JUST_NOW = "방금";
	private static final String MINUTE = "분";
	private static final String HOUR = "시간";
	private static final String DAY = "일";
	private static final int ONE_HOUR = 60;
	private static final int ONE_DAY = ONE_HOUR * 24;
	private static final int ONE_WEEK = ONE_DAY * 7;

	public static String getPastTime(LocalDateTime createTime) {
		long pastMinutes = calculatePastMinutes(createTime);
		if (pastMinutes <= 1) {
			return JUST_NOW;
		}
		if (pastMinutes < ONE_HOUR) {
			return pastMinutes + MINUTE;
		}
		if (pastMinutes < ONE_DAY) {
			return pastMinutes / ONE_HOUR + HOUR;
		}
		if (pastMinutes < ONE_WEEK) {
			return pastMinutes / ONE_DAY + DAY;
		}
		return createTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
	}

	private static long calculatePastMinutes(LocalDateTime createTime) {
		LocalDateTime now = LocalDateTime.now();
		return createTime.until(now, ChronoUnit.MINUTES);
	}
}
