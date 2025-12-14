package sunshine.weather.controller

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import sunshine.common.BusinessException
import sunshine.common.ErrorCode
import sunshine.infrastructure.openmeteo.OpenMeteoResponse
import sunshine.weather.dto.WeatherDto
import sunshine.weather.service.WeatherService

@WebMvcTest(WeatherPageController::class)
class WeatherPageControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var weatherService: WeatherService

    @Test
    fun `should return weather page view`() {
        mockMvc.perform(get("/weather"))
            .andExpect(status().isOk)
            .andExpect(view().name("weather"))
            .andExpect(model().attributeDoesNotExist("weather"))
            .andExpect(model().attributeDoesNotExist("error"))
    }

    @Test
    fun `should display weather info when city is valid`() {
        val city = "Seoul"
        val current = OpenMeteoResponse.Current(20.0, 50, 22.0, 1, 10.0)
        val summary = "Sunny in Seoul"
        val weatherDto = WeatherDto(current, summary)

        given(weatherService.getWeather(city)).willReturn(weatherDto)

        mockMvc.perform(get("/weather").param("city", city))
            .andExpect(status().isOk)
            .andExpect(view().name("weather"))
            .andExpect(model().attribute("weather", weatherDto))
            .andExpect(model().attributeDoesNotExist("error"))
    }

    @Test
    fun `should display error message when city is invalid`() {
        val city = "InvalidCity"
        given(weatherService.getWeather(city)).willThrow(BusinessException(ErrorCode.UNSUPPORTED_CITY))

        mockMvc.perform(get("/weather").param("city", city))
            .andExpect(status().isOk)
            .andExpect(view().name("weather"))
            .andExpect(model().attributeDoesNotExist("weather"))
            .andExpect(model().attribute("error", ErrorCode.UNSUPPORTED_CITY.defaultMessage))
    }
}
