package com.example.weather.domain.weather.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// timestamp → 한국 시간 변환

public class DateTimeUtils {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    // Unix timestamp → "HH시" 형태
    public static String toHour(long timestamp) {
        LocalDateTime time = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                KOREA_ZONE
        );
        return time.getHour() + "시";
    }

    // Unix timestamp → 한국시간 전체 문자열 (일출/일몰 표시에 활용)
    public static String toKoreanTime(long timestamp) {
        LocalDateTime time = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                KOREA_ZONE
        );
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Unix timestamp → "MM/dd" 날짜
    public static String toDate(long timestamp) {
        LocalDateTime date = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                KOREA_ZONE
        );
        return date.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

    public static String toDateKey(long timestamp) {
        LocalDateTime date = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                KOREA_ZONE
        );
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static int getHour(long timestamp) {
        LocalDateTime date = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                KOREA_ZONE
        );
        return date.getHour();
    }

}
