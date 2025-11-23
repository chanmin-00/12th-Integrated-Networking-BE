package com.example.weather.domain.weather.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HourlyWeatherResponse {
    private String time;   // "13시"
    private double temp;
    private String weatherIcon; // 아이콘 코드
}
