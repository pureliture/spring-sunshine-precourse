package com.sunshine.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "weather_code")
data class WeatherCode(
    @Id
    @Column(name = "code")
    val code: Int,

    @Column(name = "description", nullable = false)
    val description: String
)
