package com.example.weather.domain.weather.dto.external;

import lombok.Data;
import java.util.List;

@Data
public class AirPollutionResponse {

    private List<AirData> list;

    @Data
    public static class AirData {
        private Components components;
    }

    @Data
    public static class Components {
        private double pm10; // 미세먼지
        private double pm2_5; // 초미세먼지
    }
}
