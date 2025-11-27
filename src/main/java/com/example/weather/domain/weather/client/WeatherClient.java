package com.example.weather.domain.weather.client;

import com.example.weather.domain.weather.dto.external.OneCallResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient webClient; //

    @Value("${openweather.api.key}")
    private String apiKey;

    public Mono<OneCallResponse> getWeather(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/3.0/onecall")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .queryParam("lang", "kr")
                        .build())
                .retrieve()
                .bodyToMono(OneCallResponse.class);
    }
}