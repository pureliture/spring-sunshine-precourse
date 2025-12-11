package com.sunshine.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "city_coordinate")
class CityCoordinate(
    @Id
    @Column(name = "city_name", nullable = false, length = 50)
    val cityName: String,

    @Column(nullable = false)
    val latitude: Double,

    @Column(nullable = false)
    val longitude: Double
)
