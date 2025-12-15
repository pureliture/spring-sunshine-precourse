package sunshine.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 날씨 코드와 설명을 나타내는 엔티티.
 */
@Entity
@Table(name = "weather_code")
public class WeatherCode {

  @Id
  private Integer code;

  private String description;

  protected WeatherCode() {
  }

  public WeatherCode(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  public Integer getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
