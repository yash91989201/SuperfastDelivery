package com.example.account.ui.components.new_address

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Layers
import com.composables.icons.lucide.LocateFixed
import com.composables.icons.lucide.Lucide
import com.example.core.ui.theme.AppTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapView(
    latitude: Double,
    longitude: Double,
    onMarkCurrentLocation: () -> Unit,
    onLocationChange: (LatLng) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
    }

    val markerState = remember { MarkerState(position = LatLng(latitude, longitude)) }
    var mapType by remember { mutableStateOf(MapType.NORMAL) }
    var mapTypeMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(latitude, longitude) {
        val newLocation = LatLng(latitude, longitude)
        if (cameraPositionState.position.target != newLocation) {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(newLocation, 15f))
            markerState.position = newLocation
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(AppTheme.shape.medium)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(mapType = mapType),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                markerState.position = latLng
                onLocationChange(latLng)
            }
        ) {
            Marker(state = markerState, title = "Selected Location")
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .background(Color.White, shape = AppTheme.shape.extraSmall)
                    .size(40.dp)
                    .indication(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false
                        )
                    ),
                onClick = onMarkCurrentLocation
            ) {
                Icon(
                    imageVector = Lucide.LocateFixed,
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp),
                    contentDescription = "Mark current location"
                )
            }

            IconButton(
                modifier = Modifier
                    .background(Color.White, shape = AppTheme.shape.extraSmall)
                    .size(40.dp)
                    .indication(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false
                        )
                    ),
                onClick = { mapTypeMenuExpanded = true }
            ) {
                Icon(
                    imageVector = Lucide.Layers,
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp),
                    contentDescription = "Change map type"
                )
            }

            DropdownMenu(
                containerColor = Color.White,
                expanded = mapTypeMenuExpanded,
                onDismissRequest = { mapTypeMenuExpanded = false }
            ) {
                MapType.entries.forEach {
                    if (it != MapType.NONE) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.name.lowercase().replaceFirstChar { it.uppercase() },
                                    style = AppTheme.typography.labelMedium
                                )
                            },
                            onClick = {
                                mapType = it
                                mapTypeMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
