package sunshine.infrastructure.openmeteo

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * OpenMeteo API 응답을 매핑하는 DTO.
 */
data class OpenMeteoResponse(
    val current: Current
) {
    data class Current(
        @JsonProperty("temperature_2m")
        val temperature2m: Double,
        @JsonProperty("relative_humidity_2m")
        val relativeHumidity2m: Int,
        @JsonProperty("apparent_temperature")
        val apparentTemperature: Double,
        @JsonProperty("weather_code")
        val weatherCode: Int,
        @JsonProperty("wind_speed_10m")
        val windSpeed10m: Double
    )
}
