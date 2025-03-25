package com.example.search.data.mappers

import com.example.schema.type.ShopType as SchemaShopType
import com.example.search.domain.model.ShopType as DomainShopType

fun DomainShopType.toSchema() = when (this) {
    DomainShopType.GROCERY -> SchemaShopType.GROCERY
    DomainShopType.RESTAURANT -> SchemaShopType.RESTAURANT
    DomainShopType.PHARMACEUTICAL -> SchemaShopType.PHARMACEUTICAL
}

fun SchemaShopType.toDomain() = when (this) {
    SchemaShopType.GROCERY -> DomainShopType.GROCERY
    SchemaShopType.RESTAURANT -> DomainShopType.RESTAURANT
    SchemaShopType.PHARMACEUTICAL -> DomainShopType.PHARMACEUTICAL
    SchemaShopType.UNKNOWN__ -> DomainShopType.GROCERY
}