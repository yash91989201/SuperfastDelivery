package com.example.auth.data.mappers

import com.example.auth.domain.model.Gender as DomainGender
import com.example.core.app_state.models.Gender as StoreGender
import com.example.schema.type.Gender as SchemaGender

fun SchemaGender.toStore() = when (this) {
    SchemaGender.MALE -> StoreGender.MALE
    SchemaGender.FEMALE -> StoreGender.FEMALE
    SchemaGender.OTHERS -> StoreGender.OTHERS
    SchemaGender.UNDISCLOSED -> StoreGender.UNDISCLOSED
    SchemaGender.UNKNOWN__ -> StoreGender.UNDISCLOSED
}

fun SchemaGender.toDomain() = when (this) {
    SchemaGender.MALE -> DomainGender.MALE
    SchemaGender.FEMALE -> DomainGender.FEMALE
    SchemaGender.OTHERS -> DomainGender.OTHERS
    SchemaGender.UNDISCLOSED -> DomainGender.UNDISCLOSED
    SchemaGender.UNKNOWN__ -> DomainGender.UNDISCLOSED
}

