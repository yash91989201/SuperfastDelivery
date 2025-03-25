package com.example.search.data.mappers

import com.example.schema.ListShopsQuery
import com.example.search.domain.model.ShopImage as DomainShopImage

fun ListShopsQuery.Image.toDomain() = DomainShopImage(
    imageUrl = this.image_url,
    description = this.description
)

fun List<ListShopsQuery.Image>.toDomain() = map { it.toDomain() }