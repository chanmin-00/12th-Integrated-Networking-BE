package com.example.weather.domain.weather.service;

import com.example.weather.domain.weather.client.AirPollutionClient;
import com.example.weather.domain.weather.client.WeatherClient;
import com.example.weather.domain.weather.dto.external.AirPollutionResponse;
import com.example.weather.domain.weather.dto.external.OneCallResponse;
import com.example.weather.domain.weather.dto.response.CurrentWeatherResponse;
import com.example.weather.domain.weather.dto.response.DailyWeatherResponse;
import com.example.weather.domain.weather.dto.response.HourlyWeatherResponse;
import com.example.weather.domain.weather.dto.response.WeatherResponse;
import com.example.weather.domain.weather.util.DateTimeUtils;
import com.example.weather.domain.weather.util.WeatherConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final AirPollutionClient airPollutionClient;

    /**
     * UI가 필요로 하는 모든 날씨 정보를 가공하여 반환하는 메서드
     */
    public WeatherResponse getWeather(double lat, double lon) {

        // 1. 외부 API 호출
        OneCallResponse weather = weatherClient.getWeather(lat, lon);
        AirPollutionResponse air = airPollutionClient.getAirQuality(lat, lon);

        // 2. 가공된 현재 날씨 생성
        CurrentWeatherResponse current = buildCurrentWeather(weather, air);

        // 3. 가공된 24시간 예보 생성
        List<HourlyWeatherResponse> hourly = buildHourlyWeather(weather);

        // 4. 가공된 주간 예보 생성
        List<DailyWeatherResponse> weekly = buildDailyWeather(weather);

        // 5. 조합하여 반환
        return WeatherResponse.builder()
                .current(current)
                .hourly(hourly)
                .weekly(weekly)
                .build();
    }

    // 1) 현재 날씨 가공
    private CurrentWeatherResponse buildCurrentWeather(OneCallResponse weather, AirPollutionResponse air) {
        OneCallResponse.Current cur = weather.getCurrent();
        AirPollutionResponse.AirData airData = air.getList().get(0); // 항상 index 0

        boolean isDay = cur.getDt() >= cur.getSunrise() && cur.getDt() <= cur.getSunset();

        return CurrentWeatherResponse.builder()
                .temperature(cur.getTemp())
                .feelsLike(cur.getFeels_like())
                .weatherDescription(cur.getWeather().get(0).getDescription())
                .humidity(cur.getHumidity())
                .windDirection(WeatherConverter.windDegToDirection(cur.getWind_deg()))
                .windSpeed(cur.getWind_speed())
                .pm10Level(WeatherConverter.pm10ToLevel(airData.getComponents().getPm10()))
                .pm25Level(WeatherConverter.pm25ToLevel(airData.getComponents().getPm2_5()))
                .uvLevel(WeatherConverter.uviToLevel(cur.getUvi()))
                .sunrise(DateTimeUtils.toKoreanTime(cur.getSunrise()))
                .sunset(DateTimeUtils.toKoreanTime(cur.getSunset()))
                .isDay(isDay)
                .build();
    }

    // 2) 시간별 날씨(24시간)
    private List<HourlyWeatherResponse> buildHourlyWeather(OneCallResponse weather) {
        return weather.getHourly()
                .stream()
                .limit(24)
                .map(h -> HourlyWeatherResponse.builder()
                        .time(DateTimeUtils.toHour(h.getDt()))
                        .temp(h.getTemp())
                        .weatherIcon(h.getWeather().get(0).getIcon())
                        .build()
                ).collect(Collectors.toList());
    }

    // 3) 주간 예보(4일치)
    private List<DailyWeatherResponse> buildDailyWeather(OneCallResponse weather) {

        List<OneCallResponse.Hourly> hourlyList = weather.getHourly(); // 48시간 데이터

        return weather.getDaily()
                .stream()
                .limit(4)
                .map(daily -> {

                    // 1) daily 날짜 가져오기
                    String dailyDate = DateTimeUtils.toDateKey(daily.getDt());
                    // ex) "2025-01-23"

                    // 2) 오전 6~12시 hourly 아이콘 구하기
                    String morningIcon = hourlyList.stream()
                            .filter(h -> DateTimeUtils.toDateKey(h.getDt()).equals(dailyDate))
                            .filter(h -> {
                                int hour = DateTimeUtils.getHour(h.getDt());
                                return hour >= 6 && hour < 12;
                            })
                            .map(h -> h.getWeather().get(0).getIcon())
                            .findFirst()
                            .orElse(daily.getWeather().get(0).getIcon()); // fallback

                    // 3) 오후 18~24시 hourly 아이콘 구하기
                    String eveningIcon = hourlyList.stream()
                            .filter(h -> DateTimeUtils.toDateKey(h.getDt()).equals(dailyDate))
                            .filter(h -> {
                                int hour = DateTimeUtils.getHour(h.getDt());
                                return hour >= 18 && hour < 24;
                            })
                            .map(h -> h.getWeather().get(0).getIcon())
                            .findFirst()
                            .orElse(daily.getWeather().get(0).getIcon());

                    return DailyWeatherResponse.builder()
                            .date(DateTimeUtils.toDate(daily.getDt()))
                            .min(daily.getTemp().getMin())
                            .max(daily.getTemp().getMax())
                            .morningPop(daily.getPop() * 100)
                            .eveningPop(daily.getPop() * 100)
                            .morningIcon(morningIcon)
                            .eveningIcon(eveningIcon)
                            .build();

                })
                .collect(Collectors.toList());
    }

}
