package com.sunshine.api

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val restTemplate: RestTemplate
) {
    private lateinit var server: MockRestServiceServer

    @BeforeEach
    fun setup() {
        server = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `도시 날씨를 조회하면 성공 응답을 반환한다`() {
        server.expect(ExpectedCount.once(), requestTo(containsString("api.open-meteo.com")))
            .andRespond(withSuccess(sampleResponse(), MediaType.APPLICATION_JSON))

        mockMvc.get("/api/weather") {
            param("city", "Seoul")
        }
            .andExpect { status { isOk() } }
            .andExpect { content { string(containsString("Seoul")) } }

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
