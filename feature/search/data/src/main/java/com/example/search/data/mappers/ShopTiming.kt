package com.example.search.data.mappers

import com.example.schema.type.DayOfWeek as SchemaDayOfWeek
import java.time.DayOfWeek as DomainDayOfWeek

fun SchemaDayOfWeek.toDomain() = when (this) {
    SchemaDayOfWeek.MONDAY -> DomainDayOfWeek.MONDAY
    SchemaDayOfWeek.TUESDAY -> DomainDayOfWeek.TUESDAY
    SchemaDayOfWeek.WEDNESDAY -> DomainDayOfWeek.WEDNESDAY
    SchemaDayOfWeek.THURSDAY -> DomainDayOfWeek.THURSDAY
    SchemaDayOfWeek.FRIDAY -> DomainDayOfWeek.FRIDAY
    SchemaDayOfWeek.SATURDAY -> DomainDayOfWeek.SATURDAY
    SchemaDayOfWeek.SUNDAY -> DomainDayOfWeek.SUNDAY
    SchemaDayOfWeek.UNKNOWN__ -> DomainDayOfWeek.SUNDAY
}