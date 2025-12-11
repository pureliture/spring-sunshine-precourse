package sunshine.api.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sunshine.weather.controller.WeatherController;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    @DisplayName("Successfully returns weather info")
    void getWeather_Success() throws Exception {
        // Given
        String city = "Seoul";
        WeatherDto dto = new WeatherDto(20.0, 22.0, 0, 50, 5.0, "Sunny");
        given(weatherService.getWeather(city)).willReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/weather")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.temperature").value(20.0))
            .andExpect(jsonPath("$.data.summary").value("Sunny"));
    }

    @Test
    @DisplayName("Empty city returns validation error")
    void getWeather_EmptyCity_ReturnsError() throws Exception {
        mockMvc.perform(get("/api/v1/weather")
                .param("city", "")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"));
    }
}
