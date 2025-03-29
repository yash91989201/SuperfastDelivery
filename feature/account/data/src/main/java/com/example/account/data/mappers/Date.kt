package com.example.account.data.mappers

import java.time.LocalDate
import com.example.core.app_state.Date as ProtoDate

fun ProtoDate.toStore(): LocalDate {
    return LocalDate.of(this.year, this.month.coerceIn(1, 12), this.day + 1)
}