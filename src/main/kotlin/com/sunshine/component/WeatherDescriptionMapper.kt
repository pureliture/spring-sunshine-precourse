package com.sunshine.component

import com.sunshine.domain.WeatherCodeRepository
import org.springframework.stereotype.Component

@Component
class WeatherDescriptionMapper(
    private val weatherCodeRepository: WeatherCodeRepository
) {
    fun map(weatherCode: Int): String {
        val stored = weatherCodeRepository.findByCode(weatherCode)
        if (stored != null) {
            return stored.description
        }
        return "알 수 없는 날씨"
    }
}
