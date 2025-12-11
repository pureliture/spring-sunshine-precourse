package com.sunshine.infrastructure.adapter

import com.sunshine.api.error.ErrorCode
import com.sunshine.application.exception.WeatherException
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class OpenMeteoClient(
    private val restTemplate: RestTemplate
) {
    fun fetch(latitude: Double, longitude: Double): OpenMeteoCurrent {
        val uri = buildUri(latitude, longitude)
        val response = restTemplate.getForObject(uri, OpenMeteoResponse::class.java)
        val current = response?.current
        if (current != null) {
            return current
        }
        throw WeatherException(ErrorCode.EXTERNAL_API_ERROR, "날씨 응답 없음")
    }

    private fun buildUri(latitude: Double, longitude: Double): String {
        return UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
            .queryParam("latitude", latitude)
            .queryParam("longitude", longitude)
            .queryParam("current", "temperature_2m,apparent_temperature,weather_code,wind_speed_10m,relative_humidity_2m")
            .queryParam("timezone", "auto")
            .build()
            .toUriString()
    }
}
