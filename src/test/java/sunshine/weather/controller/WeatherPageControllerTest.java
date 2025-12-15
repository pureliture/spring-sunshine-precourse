package sunshine.weather.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sunshine.common.BusinessException;
import sunshine.common.ErrorCode;
import sunshine.infrastructure.openmeteo.OpenMeteoResponse;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

@WebMvcTest(WeatherPageController.class)
class WeatherPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WeatherService weatherService;

  @Test
  void shouldReturnWeatherPageView() throws Exception {
    mockMvc.perform(get("/weather"))
        .andExpect(status().isOk())
        .andExpect(view().name("weather"))
        .andExpect(model().attributeDoesNotExist("weather"))
        .andExpect(model().attributeDoesNotExist("error"));
  }

  @Test
  void shouldDisplayWeatherInfoWhenCityIsValid() throws Exception {
    String city = "Seoul";
    OpenMeteoResponse.Current current = new OpenMeteoResponse.Current(20.0, 50, 22.0, 1, 10.0);
    String summary = "Sunny in Seoul";
    WeatherDto weatherDto = new WeatherDto(current, summary);

    given(weatherService.getWeather(city)).willReturn(weatherDto);

    mockMvc.perform(get("/weather").param("city", city))
        .andExpect(status().isOk())
        .andExpect(view().name("weather"))
        .andExpect(model().attribute("weather", weatherDto))
        .andExpect(model().attributeDoesNotExist("error"));
  }

  @Test
  void shouldDisplayErrorMessageWhenCityIsInvalid() throws Exception {
    String city = "InvalidCity";
    given(weatherService.getWeather(city)).willThrow(new BusinessException(ErrorCode.UNSUPPORTED_CITY));

    mockMvc.perform(get("/weather").param("city", city))
        .andExpect(status().isOk())
        .andExpect(view().name("weather"))
        .andExpect(model().attributeDoesNotExist("weather"))
        .andExpect(model().attribute("error", ErrorCode.UNSUPPORTED_CITY.getDefaultMessage()));
  }
}
