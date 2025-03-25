package com.example.search.domain.model

import java.time.DayOfWeek
import java.time.LocalTime

data class ShopTiming(
    val day: DayOfWeek,
    val opensAt: LocalTime,
    val closesAt: LocalTime
)