package com.sunshine.application

import com.sunshine.api.dto.WeatherResponse
import com.sunshine.api.error.ErrorCode
import com.sunshine.application.exception.WeatherException
import com.sunshine.component.CityCoordinateProvider
import com.sunshine.component.WeatherDescriptionMapper
import com.sunshine.component.WeatherSummaryGenerator
import com.sunshine.infrastructure.adapter.OpenMeteoClient
import com.sunshine.infrastructure.adapter.OpenMeteoCurrent
import org.springframework.stereotype.Service

@Service
class WeatherFacade(
    private val cityCoordinateProvider: CityCoordinateProvider,
    private val weatherDescriptionMapper: WeatherDescriptionMapper,
    private val weatherSummaryGenerator: WeatherSummaryGenerator,
    private val openMeteoClient: OpenMeteoClient
) {
    fun getWeather(cityName: String): WeatherResponse {
        val coordinate = cityCoordinateProvider.find(cityName)
        val current = openMeteoClient.fetch(coordinate.latitude, coordinate.longitude)
        val measured = CurrentWeatherValues.from(current)
        val description = weatherDescriptionMapper.map(measured.weatherCode)
        val summary = weatherSummaryGenerator.summarize(cityName, measured.temperature, measured.apparentTemperature, measured.windSpeed, description)
        return WeatherResponse(cityName, measured.temperature, measured.apparentTemperature, measured.windSpeed, measured.humidity, description, summary)
    }
}

data class CurrentWeatherValues(
    val temperature: Double,
    val apparentTemperature: Double,
    val windSpeed: Double,
    val weatherCode: Int,
    val humidity: Int
) {
    companion object {
        fun from(current: OpenMeteoCurrent): CurrentWeatherValues {
            val temperature = current.temperature_2m ?: throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "기온 정보가 없습니다.")
            val apparentTemperature = current.apparent_temperature ?: throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "체감 온도 정보가 없습니다.")
            val windSpeed = current.wind_speed_10m ?: throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "풍속 정보가 없습니다.")
            val weatherCode = current.weather_code ?: throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "날씨 코드가 없습니다.")
            val humidity = current.relative_humidity_2m ?: throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "습도 정보가 없습니다.")
            return CurrentWeatherValues(temperature, apparentTemperature, windSpeed, weatherCode, humidity)
        }
    }
}
