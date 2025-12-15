package sunshine.city.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 도시 정보를 나타내는 엔티티.
 */
@Entity
@Table(name = "city")
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Double latitude;

  private Double longitude;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  protected City() {
  }

  public City(Long id, String name, Double latitude, Double longitude, Country country) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
  }

  public City(String name, Double latitude, Double longitude, Country country) {
    this(null, name, latitude, longitude, country);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Country getCountry() {
    return country;
  }
}
