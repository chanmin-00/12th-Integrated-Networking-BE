package com.example.weather.domain.weather.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyWeatherResponse {
    private String date;
    private double min; // 오전 최저 온도
    private double max; // 오후 최고 온도
    private double morningPop; // 오전 강수 확률
    private double eveningPop; // 오후 강수 확률
    private String morningIcon;
    private String eveningIcon;
}
