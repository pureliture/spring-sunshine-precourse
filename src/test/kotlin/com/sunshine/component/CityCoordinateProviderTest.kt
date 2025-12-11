package com.sunshine.component

import com.sunshine.application.exception.WeatherException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CityCoordinateProviderTest @Autowired constructor(
    private val cityCoordinateProvider: CityCoordinateProvider
) {
    @Test
    fun `지원 도시 좌표를 반환한다`() {
        val result = cityCoordinateProvider.find("Seoul")
        assertThat(result.latitude).isEqualTo(37.5665)
        assertThat(result.longitude).isEqualTo(126.9780)
    }

    @Test
    fun `미지원 도시는 예외가 발생한다`() {
        assertThatThrownBy { cityCoordinateProvider.find("Mars") }
            .isInstanceOf(WeatherException::class.java)
    }
}
