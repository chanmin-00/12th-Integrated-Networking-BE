package com.example.weather.domain.weather.controller;

import com.example.weather.domain.weather.dto.response.WeatherResponse;
import com.example.weather.domain.weather.service.WeatherService;
import com.example.weather.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    @Operation(
            summary = "날씨 조회 API",
            description = "위도(lat), 경도(lon)를 기반으로 [날씨 현황], [시간별 현황], [주간 예보] 모두 조회합니다."
    )
    public ApiResponse<WeatherResponse> getWeather(
            @Parameter(description = "위도") @RequestParam double lat,
            @Parameter(description = "경도") @RequestParam double lon
    ) {
        WeatherResponse response = weatherService.getWeather(lat, lon);
        return ApiResponse.success(response);
    }
}