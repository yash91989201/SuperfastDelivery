package com.example.auth.data.mappers

import com.example.auth.domain.model.AuthRole as DomainAuthRole
import com.example.core.app_state.AuthRole as ProtoAuthRole
import com.example.core.app_state.models.AuthRole as StoreAuthRole
import com.example.schema.type.AuthRole as SchemaAuthRole

fun ProtoAuthRole.toStore() = when (this) {
    ProtoAuthRole.CUSTOMER -> StoreAuthRole.CUSTOMER
    ProtoAuthRole.DELIVERY_PARTNER -> StoreAuthRole.DELIVERY_PARTNER
    ProtoAuthRole.VENDOR -> StoreAuthRole.VENDOR
    ProtoAuthRole.ADMIN -> StoreAuthRole.ADMIN
    ProtoAuthRole.UNRECOGNIZED -> StoreAuthRole.CUSTOMER
}

fun SchemaAuthRole.toDomain() = when (this) {
    SchemaAuthRole.CUSTOMER -> DomainAuthRole.CUSTOMER
    SchemaAuthRole.DELIVERY_PARTNER -> DomainAuthRole.DELIVERY_PARTNER
    SchemaAuthRole.VENDOR -> DomainAuthRole.VENDOR
    SchemaAuthRole.ADMIN -> DomainAuthRole.ADMIN
    SchemaAuthRole.UNKNOWN__ -> DomainAuthRole.CUSTOMER
}

fun SchemaAuthRole.toStore() = when (this) {
    SchemaAuthRole.CUSTOMER -> StoreAuthRole.CUSTOMER
    SchemaAuthRole.DELIVERY_PARTNER -> StoreAuthRole.DELIVERY_PARTNER
    SchemaAuthRole.VENDOR -> StoreAuthRole.VENDOR
    SchemaAuthRole.ADMIN -> StoreAuthRole.ADMIN
    SchemaAuthRole.UNKNOWN__ -> StoreAuthRole.CUSTOMER
}

fun StoreAuthRole.toProto() = when (this) {
    StoreAuthRole.CUSTOMER -> ProtoAuthRole.CUSTOMER
    StoreAuthRole.DELIVERY_PARTNER -> ProtoAuthRole.DELIVERY_PARTNER
    StoreAuthRole.VENDOR -> ProtoAuthRole.VENDOR
    StoreAuthRole.ADMIN -> ProtoAuthRole.ADMIN
}

fun DomainAuthRole.toSchema() = when (this) {
    DomainAuthRole.CUSTOMER -> SchemaAuthRole.CUSTOMER
    DomainAuthRole.DELIVERY_PARTNER -> SchemaAuthRole.DELIVERY_PARTNER
    DomainAuthRole.VENDOR -> SchemaAuthRole.VENDOR
    DomainAuthRole.ADMIN -> SchemaAuthRole.ADMIN
}

