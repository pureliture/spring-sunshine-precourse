package sunshine.infrastructure.restclient.nominatim;

import java.util.List;

public record NominatimResponse(
    String lat,
    String lon,
    String display_name
) {
}
