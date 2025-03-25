package com.example.search.domain.model

enum class OrderBy {
    ASC, DESC
}

data class Shop(
    val id: String,
    val name: String,
    val shopType: ShopType,
    val shopStatus: ShopStatus,
    val address: ShopAddress,
    // val timings: List<ShopTiming>,
    val image: List<ShopImage>
)

data class ListShopsInput(
    val name: String? = null,
    val shopType: ShopType? = null,
    val shopStatus: ShopStatus? = null,
    val orderBy: OrderBy? = null,
    val limit: Int? = null,
    val offset: Int? = null
)