package sunshine.infrastructure.restclient.nominatim;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import sunshine.city.component.Coordinate;
import sunshine.common.exception.model.infrastructure.RestApiException;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;

@Component
public class NominatimClient {

  private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";
  private final RestClient restClient;

  public NominatimClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public Coordinate search(String city) {

    String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("q", city)
            .queryParam("format", "json")
            .queryParam("limit", 1)
            .build()
            .toUriString();

    try {

      List<NominatimResponse> responses = restClient.get()
              .uri(url)
              .retrieve()
              .body(new ParameterizedTypeReference<List<NominatimResponse>>() {});

      if (responses != null && !responses.isEmpty()) {
          NominatimResponse response = responses.get(0);
          return new Coordinate(Double.parseDouble(response.lat()), Double.parseDouble(response.lon()));
      }
      return null;
    } catch (RestClientException e) {
      throw new RestApiException("Nominatim API Error", e);
    }
  }
}
