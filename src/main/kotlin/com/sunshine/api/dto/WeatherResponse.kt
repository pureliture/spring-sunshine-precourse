package com.sunshine.api.dto

data class WeatherResponse(
    val city: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val windSpeed: Double,
    val humidity: Int,
    val description: String,
    val summary: String
)
