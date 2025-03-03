package com.example.account.ui.screens.create_profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.account.domain.model.CreateProfileInput
import com.example.common.utils.UiText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.example.account.domain.model.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    viewModel: CreateProfileViewModel,
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
                        Log.d("CreateProfile", "Create Profile Button Clicked")
                        viewModel.onEvent(
                            CreateProfile.Event.CreateProfile(
                                CreateProfileInput(
                                    name = name,
                                    imageUrl = imageUrl,
                                    dob = dob,
                                    anniversary = anniversary,
                                    gender = gender ?: Gender.OTHERS,
                                    authId = "culamzg0nhao3ufocwargza9" // Replace with actual auth ID
                                )
                            )
                        )
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