package com.sunshine.infrastructure.adapter

data class OpenMeteoResponse(
    val current: OpenMeteoCurrent?
)

data class OpenMeteoCurrent(
    val time: String?,
    val temperature_2m: Double?,
    val apparent_temperature: Double?,
    val wind_speed_10m: Double?,
    val weather_code: Int?,
    val relative_humidity_2m: Int?
)
