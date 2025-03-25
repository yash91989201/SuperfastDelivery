package com.example.search.data.mappers

import com.example.schema.ListShopsQuery
import com.example.search.domain.model.ShopAddress as DomainShopAddress


fun ListShopsQuery.Address.toDomain() = DomainShopAddress(
    address = this.address,
    nearbyLandmark = this.nearby_landmark
)