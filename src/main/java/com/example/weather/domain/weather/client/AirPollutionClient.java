package com.example.weather.domain.weather.client;

import com.example.weather.domain.weather.dto.external.AirPollutionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AirPollutionClient {

    private final WebClient webClient;

    @Value("${openweather.api.key}")
    private String apiKey;

    public Mono<AirPollutionResponse> getAirQuality(double lat, double lon) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/air_pollution")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(AirPollutionResponse.class);
    }
}