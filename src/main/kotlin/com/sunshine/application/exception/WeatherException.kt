package com.sunshine.application.exception

import com.sunshine.api.error.ErrorCode

class WeatherException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message)
