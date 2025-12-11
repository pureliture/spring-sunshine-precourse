package sunshine.weather.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 날씨 코드와 설명을 나타내는 엔티티.
 */
@Entity
@Table(name = "weather_code")
class WeatherCode(
    @Id
    val code: Int,

    val description: String
) {
    protected constructor() : this(code = 0, description = "")
}
