package sunshine.infrastructure.restclient.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoResponse(
    Current current,
    Daily daily
) {

    public record Current(
        @JsonProperty("temperature_2m") double temperature2m,
        @JsonProperty("apparent_temperature") double apparentTemperature,
        @JsonProperty("relative_humidity_2m") int relativeHumidity2m,
        @JsonProperty("wind_speed_10m") double windSpeed10m,
        @JsonProperty("weather_code") int weatherCode,
        @JsonProperty("precipitation") double precipitation
    ) {}

    public record Daily(
         @JsonProperty("temperature_2m_max") double[] temperature2mMax
    ) {}
}
