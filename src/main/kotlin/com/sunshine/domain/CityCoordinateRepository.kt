package com.sunshine.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CityCoordinateRepository : JpaRepository<CityCoordinate, String> {
    fun findByCityNameIgnoreCase(cityName: String): CityCoordinate?
}
