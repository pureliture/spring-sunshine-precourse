package sunshine.infrastructure.openmeteo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sunshine.common.BusinessException;
import sunshine.common.ErrorCode;

@Component
public class OpenMeteoClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public OpenMeteoClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public OpenMeteoResponse fetchWeather(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("current", "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m")
                .build()
                .toUriString();

        try {
            return restTemplate.getForObject(url, OpenMeteoResponse.class);
        } catch (RestClientException e) {
            throw new BusinessException(ErrorCode.EXTERNAL_API_ERROR, e);
        }
    }
}
