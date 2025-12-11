package com.sunshine.component

import org.springframework.stereotype.Component

@Component
class WeatherSummaryGenerator {
    fun summarize(city: String, temperature: Double, apparentTemperature: Double, windSpeed: Double, description: String): String {
        val roundedTemperature = String.format("%.1f", temperature)
        val roundedApparent = String.format("%.1f", apparentTemperature)
        val roundedWind = String.format("%.1f", windSpeed)
        return "현재 ${city}의 기온은 ${roundedTemperature}°C, 체감 온도는 ${roundedApparent}°C, 풍속은 ${roundedWind}m/s입니다. 날씨는 ${description}입니다."
    }
}
