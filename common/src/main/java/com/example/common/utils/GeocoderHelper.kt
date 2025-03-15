package com.example.common.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class GeocoderHelper(private val context: Context) {
    suspend fun getAddress(lat: Double, lng: Double): String? = withContext(Dispatchers.IO) {
        runCatching {
            Geocoder(context, Locale.getDefault()).getFromLocation(lat, lng, 1)
                ?.firstOrNull()
                ?.let {
                    Log.d("check", "address: $it")
                    formatAddress(it)
                }
        }.getOrNull()
    }

    private fun formatAddress(address: android.location.Address): String =
        address.getAddressLine(0)
            ?: listOfNotNull(
                address.thoroughfare,
                address.subThoroughfare,
                address.locality,
                address.adminArea,
                address.countryName
            ).takeIf { it.isNotEmpty() }?.joinToString(", ")
            ?: "Unknown Location"
}