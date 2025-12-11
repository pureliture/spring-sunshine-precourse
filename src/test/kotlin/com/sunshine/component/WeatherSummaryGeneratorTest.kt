package com.sunshine.component

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WeatherSummaryGeneratorTest @Autowired constructor(
    private val weatherSummaryGenerator: WeatherSummaryGenerator
) {
    @Test
    fun `요약 문장을 생성한다`() {
        val summary = weatherSummaryGenerator.summarize("서울", 3.4, 2.1, 5.7, "흐림")
        assertThat(summary).contains("서울")
    }
}
