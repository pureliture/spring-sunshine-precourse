package sunshine.city.component

import org.springframework.stereotype.Component
import sunshine.city.repository.CityRepository
import sunshine.common.BusinessException
import sunshine.common.ErrorCode

/**
 * 도시 이름을 기반으로 해당 도시의 정보를 제공하는 컴포넌트.
 */
@Component
class CityCoordinateMapper(
    private val cityRepository: CityRepository
) {

    /**
     * 주어진 도시 이름에 해당하는 좌표를 반환한다.
     *
     * @param cityName 좌표를 조회할 도시 이름
     * @return 해당 도시의 좌표 (위도, 경도)
     * @throws BusinessException 지원하지 않는 도시일 경우
     */
    fun getCoordinate(cityName: String?): Coordinate {
        requireNotNull(cityName) { "City name cannot be null" }

        val city = cityRepository.findByName(cityName.lowercase())
            ?: throw BusinessException(ErrorCode.UNSUPPORTED_CITY)

        return Coordinate(city.latitude, city.longitude)
    }
}
