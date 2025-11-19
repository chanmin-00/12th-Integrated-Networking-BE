package com.example.weather.domain.weather.dto.external;

import lombok.Data;
import java.util.List;

@Data
public class OneCallResponse {
    private Current current;
    private List<Hourly> hourly;
    private List<Daily> daily;

    @Data
    public static class Current {
        private long dt; // 현재 날씨가 측정된 시각을 나타내는 필드
        private double temp; // 온도
        private double feels_like; // 체감 온도
        private int humidity; // 습도
        private double wind_speed; // 풍속
        private int wind_deg; // 풍향
        private double uvi; // 자외선 (등급 기준 필요)
        private long sunrise; // 일출
        private long sunset; // 일몰
        private List<Weather> weather;
    }

    @Data
    public static class Hourly { // 1시간 단위 예보 (index 0~23)
        private long dt;
        private double temp;
        private List<Weather> weather;
    }

    @Data
    public static class Daily {
        private long dt;
        private Temp temp;
        private double pop; // 강수 확률
        private List<Weather> weather;
    }

    @Data
    public static class Temp {
        private double min; // 하루 최저 온도
        private double max; // 하루 최고 온도
        private double morn; // 아침 시간대의 평균 온도
        private double eve; // 저녁 시간대의 평균 온도
    }

    @Data
    public static class Weather {
        private String description; // 날씨 상태(ex. 맑음)
        private String icon; // 날씨 아이콘
    }
}
