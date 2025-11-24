package com.example.weather.domain.weather.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentWeatherResponse {
    private String date;
    private double temperature;
    private double feelsLike;
    private String weatherDescription;
    private int humidity;
    private String windDirection;
    private double windSpeed;
    private String weatherIcon; // 아이콘 코드

    private String pm10Level;      // 좋음/보통/나쁨/매우 나쁨
    private String pm25Level;      // 좋음/보통/나쁨/매우 나쁨
    private String uvLevel;        // 낮음/중간/높음/위험

    private String sunrise;        // "06:42"
    private String sunset;         // "17:12"
    private boolean isDay;         // true = 낮, false = 밤
}

