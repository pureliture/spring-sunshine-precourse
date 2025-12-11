package com.sunshine.component

import com.sunshine.application.exception.WeatherException
import com.sunshine.domain.CityCoordinateRepository
import com.sunshine.api.error.ErrorCode
import org.springframework.stereotype.Component

@Component
class CityCoordinateProvider(
    private val cityCoordinateRepository: CityCoordinateRepository
) {
    fun find(cityName: String): CityCoordinateResult {
        val stored = cityCoordinateRepository.findByCityNameIgnoreCase(cityName)
        if (stored != null) {
            return CityCoordinateResult(stored.latitude, stored.longitude)
        }
        throw WeatherException(ErrorCode.CITY_NOT_SUPPORTED, "지원하지 않는 도시: $cityName")
    }
}

data class CityCoordinateResult(
    val latitude: Double,
    val longitude: Double
)
