package com.example.weather.domain.weather.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentWeatherResponse {
    private double temperature;
    private double feelsLike;
    private String weatherDescription;
    private int humidity;
    private String windDirection;
    private double windSpeed;

    private String pm10Level;      // 매우 좋음/좋음/보통/나쁨/매우 나쁨
    private String pm25Level;      // 매우 좋음/좋음/보통/나쁨/매우 나쁨
    private String uvLevel;        // 낮음/보통/높음/매우 높음/위험

    private String sunrise;        // "06:42"
    private String sunset;         // "17:12"
    private boolean isDay;         // true = 낮, false = 밤
}

