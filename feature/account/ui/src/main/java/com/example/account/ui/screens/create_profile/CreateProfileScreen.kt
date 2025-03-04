package com.example.account.ui.screens.create_profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Gender
import com.example.common.application_state_store.ApplicationStateStore
import com.example.common.utils.UiText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    viewModel: CreateProfileViewModel,
    applicationStateStore: ApplicationStateStore,
    onNavigateToSearch: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf<Gender?>(null) }
    var dob by remember { mutableStateOf<LocalDate?>(null) }
    var anniversary by remember { mutableStateOf<LocalDate?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    var showDobPicker by remember { mutableStateOf(false) }
    var showAnniversaryPicker by remember { mutableStateOf(false) }

    val session by applicationStateStore.sessionStateHolder.session.collectAsState()
    Log.d("session","Current session: $session")
    val authId = session?.authId

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { nav ->
            when (nav) {
                CreateProfile.Navigation.GoToSearchScreen -> onNavigateToSearch()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Create Profile") })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileImagePicker(imageUrl) { imageUrl = it }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                GenderSelection(gender) { gender = it }

                DateSelectionField(
                    label = "Date of Birth",
                    date = dob,
                    onDateSelected = { dob = it },
                    showPicker = showDobPicker,
                    onShowPicker = { showDobPicker = true },
                    onDismiss = { showDobPicker = false }
                )

                DateSelectionField(
                    label = "Anniversary",
                    date = anniversary,
                    onDateSelected = { anniversary = it },
                    showPicker = showAnniversaryPicker,
                    onShowPicker = { showAnniversaryPicker = true },
                    onDismiss = { showAnniversaryPicker = false }
                )

                Button(
                    onClick = {
                        if (authId != null) {
                            viewModel.onEvent(
                                CreateProfile.Event.CreateProfile(
                                    CreateProfileInput(
                                        name = name,
                                        imageUrl = imageUrl,
                                        dob = dob,
                                        anniversary = anniversary,
                                        gender = gender ?: Gender.OTHERS,
                                        authId = authId
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && gender != null
                ) {
                    Text("Create Profile")
                }
            }

            uiState.error.let { error ->
                if (error !is UiText.Idle) {
                    val message = when (error) {
                        is UiText.RemoteString -> error.message
                        else -> "An error occurred"
                    }
                    LaunchedEffect(error) {
                        SnackbarHostState().showSnackbar(message)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileImagePicker(
    imageUrl: String?,
    onImageSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .clickable { onImageSelected("https://picsum.photos/200") },
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                Icons.Default.Person,
                contentDescription = "Add Photo",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenderSelection(
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Gender",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Gender.entries.forEach { gender ->
                val displayName = when (gender) {
                    Gender.MALE -> "Male"
                    Gender.FEMALE -> "Female"
                    Gender.OTHERS -> "Others"
                    Gender.UNDISCLOSED -> "Prefer not to say"
                }

                FilterChip(
                    selected = selectedGender == gender,
                    onClick = { onGenderSelected(gender) },
                    label = { Text(displayName) },
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun DateSelectionField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    showPicker: Boolean,
    onShowPicker: () -> Unit,
    onDismiss: () -> Unit
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedButton(
            onClick = onShowPicker,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = date?.format(dateFormatter) ?: "Select $label")
        }
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            selectedDate = date ?: LocalDate.now(),
            onDateSelected = { onDateSelected(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay()
            .atZone(java.time.ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                    onDismissRequest()
                }
            ) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancel") }
        },
        title = { Text("Select Date") },
        text = {
            DatePicker(state = datePickerState)
        }
    )
}