package com.sunshine.api.controller

import com.sunshine.api.dto.WeatherResponse
import com.sunshine.api.response.ApiResponse
import com.sunshine.application.WeatherFacade
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/weather")
@Validated
class WeatherController(
    private val weatherFacade: WeatherFacade
) {
    @GetMapping
    fun getWeather(@RequestParam("city") @NotBlank(message = "도시 이름을 입력하세요.") city: String): ApiResponse<WeatherResponse> {
        val response = weatherFacade.getWeather(city)
        return ApiResponse.success(response)
    }
}
