package com.example.account.data.mappers

import com.example.account.domain.model.Gender as DomainGender
import com.example.common.Gender as ProtoGender
import com.example.common.models.Gender as StoreGender
import com.example.schema.type.Gender as SchemaGender

fun ProtoGender.toStore(): StoreGender = when (this) {
    ProtoGender.MALE -> StoreGender.MALE
    ProtoGender.FEMALE -> StoreGender.FEMALE
    ProtoGender.OTHERS -> StoreGender.OTHERS
    ProtoGender.UNDISCLOSED -> StoreGender.UNDISCLOSED
    ProtoGender.UNRECOGNIZED -> StoreGender.UNDISCLOSED
}

fun StoreGender.toProto(): ProtoGender = when (this) {
    StoreGender.MALE -> ProtoGender.MALE
    StoreGender.FEMALE -> ProtoGender.FEMALE
    StoreGender.OTHERS -> ProtoGender.OTHERS
    StoreGender.UNDISCLOSED -> ProtoGender.UNDISCLOSED
}

fun StoreGender.toDomain(): DomainGender = when (this) {
    StoreGender.MALE -> DomainGender.MALE
    StoreGender.FEMALE -> DomainGender.FEMALE
    StoreGender.OTHERS -> DomainGender.OTHERS
    StoreGender.UNDISCLOSED -> DomainGender.UNDISCLOSED
}

fun DomainGender.toSchema() = when (this) {
    DomainGender.MALE -> SchemaGender.MALE
    DomainGender.FEMALE -> SchemaGender.FEMALE
    DomainGender.OTHERS -> SchemaGender.OTHERS
    DomainGender.UNDISCLOSED -> SchemaGender.UNDISCLOSED
}

fun SchemaGender.toDomain() = when (this) {
    SchemaGender.MALE -> DomainGender.MALE
    SchemaGender.FEMALE -> DomainGender.FEMALE
    SchemaGender.OTHERS -> DomainGender.OTHERS
    SchemaGender.UNDISCLOSED -> DomainGender.UNDISCLOSED
    SchemaGender.UNKNOWN__ -> DomainGender.UNDISCLOSED
}