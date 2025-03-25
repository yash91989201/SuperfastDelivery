package com.example.account.data.mappers

import com.apollographql.apollo.api.Optional
import com.example.schema.CreateDeliveryAddressMutation
import com.example.schema.GetDefaultDeliveryAddressQuery
import com.example.schema.ListDeliveryAddressQuery
import com.example.account.domain.model.AddressAlias as DomainAddressAlias
import com.example.account.domain.model.CreateDeliveryAddressInput as DomainCreateDeliveryAddressInput
import com.example.account.domain.model.DefaultDeliveryAddress as DomainDefaultDeliveryAddress
import com.example.account.domain.model.DeliveryAddress as DomainDeliveryAddress
import com.example.account.domain.model.ListDeliveryAddress as DomainListDeliveryAddress
import com.example.schema.type.AddressAlias as SchemaAddressAlias
import com.example.schema.type.CreateDeliveryAddressInput as SchemaCreateDeliveryAddressInput

fun SchemaAddressAlias.toDomain(): DomainAddressAlias {
    return when (this) {
        SchemaAddressAlias.HOME -> DomainAddressAlias.HOME
        SchemaAddressAlias.WORK -> DomainAddressAlias.WORK
        SchemaAddressAlias.HOTEL -> DomainAddressAlias.HOTEL
        SchemaAddressAlias.OTHER -> DomainAddressAlias.OTHERS
        SchemaAddressAlias.UNKNOWN__ -> DomainAddressAlias.OTHERS
    }
}

fun DomainAddressAlias.toSchema(): SchemaAddressAlias {
    return when (this) {
        DomainAddressAlias.HOME -> SchemaAddressAlias.HOME
        DomainAddressAlias.WORK -> SchemaAddressAlias.WORK
        DomainAddressAlias.HOTEL -> SchemaAddressAlias.HOTEL
        DomainAddressAlias.OTHERS -> SchemaAddressAlias.OTHER
    }
}


fun DomainCreateDeliveryAddressInput.toSchema() = SchemaCreateDeliveryAddressInput(
    receiver_name = this.receiverName,
    receiver_phone = this.receiverPhone,
    address_alias = this.addressAlias.toSchema(),
    other_alias = Optional.presentIfNotNull(this.otherAlias),
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    nearby_landmark = Optional.presentIfNotNull(this.nearbyLandmark),
    delivery_instruction = Optional.presentIfNotNull(this.deliveryInstruction),
    is_default = this.isDefault,
    auth_id = this.authId
)

fun CreateDeliveryAddressMutation.CreateDeliveryAddress.toDomain() = DomainDeliveryAddress(
    id = this.id,
    receiverName = this.receiver_name,
    receiverPhone = this.receiver_phone,
    addressAlias = this.address_alias.toDomain(),
    otherAlias = this.other_alias,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    nearbyLandmark = this.nearby_landmark,
    deliveryInstruction = this.delivery_instruction,
    isDefault = this.is_default,
    authId = this.auth_id,
)


fun GetDefaultDeliveryAddressQuery.GetDefaultDeliveryAddress.toDomain() =
    DomainDefaultDeliveryAddress(
        address = this.address,
        addressAlias = this.address_alias.toDomain()
    )


fun ListDeliveryAddressQuery.Delivery_address.toDomain() = DomainDeliveryAddress(
    id = this.id,
    receiverName = this.receiver_name,
    receiverPhone = this.receiver_phone,
    addressAlias = this.address_alias.toDomain(),
    otherAlias = this.other_alias,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    nearbyLandmark = this.nearby_landmark,
    deliveryInstruction = this.delivery_instruction,
    isDefault = this.is_default,
    authId = this.auth_id,
)

fun List<ListDeliveryAddressQuery.Delivery_address>.toDomain() = map { it.toDomain() }

fun ListDeliveryAddressQuery.ListDeliveryAddress.toDomain() = DomainListDeliveryAddress(
    deliveryAddress = this.delivery_address.toDomain()
)