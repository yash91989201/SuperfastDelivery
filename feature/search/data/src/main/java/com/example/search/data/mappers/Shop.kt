package com.example.search.data.mappers

import com.apollographql.apollo.api.Optional
import com.example.schema.ListShopsQuery
import com.example.schema.type.ListShopsInput as SchemaListShopInput
import com.example.schema.type.OrderBy as SchemaOrderBy
import com.example.search.domain.model.ListShopsInput as DomainListShopsInput
import com.example.search.domain.model.OrderBy as DomainOrderBy
import com.example.search.domain.model.Shop as DomainShop


fun DomainOrderBy.toSchema() = when (this) {
    DomainOrderBy.ASC -> SchemaOrderBy.ASC
    DomainOrderBy.DESC -> SchemaOrderBy.DESC
}

fun DomainListShopsInput.toSchema() = SchemaListShopInput(
    name = Optional.presentIfNotNull(this.name),
    shop_type = Optional.presentIfNotNull(this.shopType?.toSchema()),
    shop_status = Optional.presentIfNotNull(this.shopStatus?.toSchema()),
    order_by = Optional.presentIfNotNull(this.orderBy?.toSchema()),
    limit = Optional.presentIfNotNull(this.limit),
    offset = Optional.presentIfNotNull(this.offset)
)


fun ListShopsQuery.Shop.toDomain() = DomainShop(
    id = this.id,
    name = this.name,
    shopType = this.shop_type.toDomain(),
    shopStatus = this.shop_status.toDomain(),
    address = this.address.toDomain(),
    image = this.images.toDomain()
)

fun List<ListShopsQuery.Shop>.toDomain() = map { it.toDomain() }

fun ListShopsQuery.ListShops.toDomain() = this.shops.toDomain()

