package com.sunshine.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate
import org.hamcrest.Matchers.containsString

@SpringBootTest
class WeatherFacadeTest @Autowired constructor(
    private val weatherFacade: WeatherFacade,
    private val restTemplate: RestTemplate
) {
    private lateinit var server: MockRestServiceServer

    @BeforeEach
    fun setup() {
        server = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `날씨 정보를 조회한다`() {
        server.expect(ExpectedCount.once(), requestTo(containsString("api.open-meteo.com")))
            .andRespond(withSuccess(sampleResponse(), MediaType.APPLICATION_JSON))

        val response = weatherFacade.getWeather("Seoul")

        assertThat(response.city).isEqualTo("Seoul")
        assertThat(response.description).isEqualTo("흐림")
        server.verify()
    }

    private fun sampleResponse(): String {
        return """
            {"current":{
                "time":"2024-07-24T00:00",
                "temperature_2m":3.4,
                "apparent_temperature":2.1,
                "wind_speed_10m":5.7,
                "weather_code":3,
                "relative_humidity_2m":40
            }}
        """.trimIndent()
    }
}
