package sunshine.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunshine.weather.domain.WeatherCode;

/**
 * 날씨 코드 정보에 접근하기 위한 리포지토리.
 */
public interface WeatherCodeRepository extends JpaRepository<WeatherCode, Integer> {
}
