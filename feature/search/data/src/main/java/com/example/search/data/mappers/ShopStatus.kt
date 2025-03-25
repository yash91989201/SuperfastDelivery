package com.example.search.data.mappers

import com.example.schema.type.ShopStatus as SchemaShopStatus
import com.example.search.domain.model.ShopStatus as DomainShopStatus

fun DomainShopStatus.toSchema() = when (this) {
    DomainShopStatus.OPEN -> SchemaShopStatus.OPEN
    DomainShopStatus.CLOSED -> SchemaShopStatus.CLOSED
}

fun SchemaShopStatus.toDomain() = when (this) {
    SchemaShopStatus.OPEN -> DomainShopStatus.OPEN
    SchemaShopStatus.CLOSED -> DomainShopStatus.CLOSED
    SchemaShopStatus.UNKNOWN__ -> DomainShopStatus.CLOSED
}