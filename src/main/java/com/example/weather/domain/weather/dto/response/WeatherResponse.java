package com.example.weather.domain.weather.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class WeatherResponse {

    private CurrentWeatherResponse current;
    private List<HourlyWeatherResponse> hourly;
    private List<DailyWeatherResponse> weekly;
}
