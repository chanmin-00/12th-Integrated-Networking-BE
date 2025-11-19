package com.example.weather.domain.weather.util;


// 풍향, 미세먼지 등급, 자외선 등급 변환 담당
public class WeatherConverter {

    // 풍향(degree → 동/서/남/북)
    public static String windDegToDirection(int deg) {
        if (deg >= 337.5 || deg < 22.5) return "북풍";
        if (deg >= 22.5 && deg < 67.5) return "북동풍";
        if (deg >= 67.5 && deg < 112.5) return "동풍";
        if (deg >= 112.5 && deg < 157.5) return "남동풍";
        if (deg >= 157.5 && deg < 202.5) return "남풍";
        if (deg >= 202.5 && deg < 247.5) return "남서풍";
        if (deg >= 247.5 && deg < 292.5) return "서풍";
        return "북서풍";
    }

    // 미세먼지(PM10) 등급
    public static String pm10ToLevel(double value) {
        if (value <= 30) return "좋음";
        if (value <= 80) return "보통";
        if (value <= 150) return "나쁨";
        return "매우 나쁨";
    }

    // 초미세먼지(PM2.5) 등급
    public static String pm25ToLevel(double value) {
        if (value <= 15) return "좋음";
        if (value <= 35) return "보통";
        if (value <= 75) return "나쁨";
        return "매우 나쁨";
    }


    // 자외선 지수 UVI 등급 (세계보건기구 기준)
    public static String uviToLevel(double uvi) {
        if (uvi < 3) return "낮음";
        if (uvi < 6) return "보통";
        if (uvi < 8) return "높음";
        if (uvi < 11) return "매우 높음";
        return "위험";
    }
}
