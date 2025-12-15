package sunshine.city.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 국가 정보를 나타내는 엔티티.
 */
@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  protected Country() {
  }

  public Country(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Country(String name) {
    this(null, name);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
