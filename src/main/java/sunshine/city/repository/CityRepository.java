package sunshine.city.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunshine.city.domain.City;

import java.util.Optional;

/**
 * 도시 정보에 접근하기 위한 리포지토리.
 */
public interface CityRepository extends JpaRepository<City, Long> {
  Optional<City> findByName(String name);
}
