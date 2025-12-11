package com.sunshine.domain

import org.springframework.data.jpa.repository.JpaRepository

interface WeatherCodeRepository : JpaRepository<WeatherCode, Int> {
    fun findByCode(code: Int): WeatherCode?
}
