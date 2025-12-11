package com.sunshine.component

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WeatherDescriptionMapperTest @Autowired constructor(
    private val weatherDescriptionMapper: WeatherDescriptionMapper
) {
    @Test
    fun `날씨 코드를 설명으로 변환한다`() {
        val description = weatherDescriptionMapper.map(3)
        assertThat(description).isEqualTo("흐림")
    }
}
