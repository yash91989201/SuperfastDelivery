package com.example.account.ui.screens.create_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Gender
import com.example.account.ui.components.create_profile.DateSelectionField
import com.example.account.ui.components.create_profile.ProfileImagePicker
import com.example.core.ui.components.DropdownInput
import com.example.core.ui.components.FullScreenLoader
import com.example.core.ui.components.TextInput
import com.example.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel
) {
    var showDobPicker by remember { mutableStateOf(false) }
    var showAnniversaryPicker by remember { mutableStateOf(false) }
    var genderDropdownExpanded by remember { mutableStateOf(false) }

    val auth by viewModel.auth.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val name by viewModel.name.collectAsStateWithLifecycle()
    val gender by viewModel.gender.collectAsStateWithLifecycle()
    val dob by viewModel.dob.collectAsStateWithLifecycle()
    val anniversary by viewModel.anniversary.collectAsStateWithLifecycle()
    val imageUrl by viewModel.imageUrl.collectAsStateWithLifecycle()

    val nameError by viewModel.nameError.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Create Profile",
                        style = AppTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                }
            )
        },
        modifier = modifier.padding(top = 0.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ProfileImagePicker(imageUrl) { viewModel.updateImageUrl(it) }

            Spacer(modifier = Modifier.height(16.dp))

            TextInput(
                value = name,
                onValueChange = { viewModel.updateName(it) },
                placeholderText = "Full Name",
                isError = nameError != null,
                errorMessage = nameError
            )

            DropdownInput(
                items = Gender.entries,
                selectedItem = gender,
                onItemSelected = { viewModel.updateGender(it) },
                expanded = genderDropdownExpanded,
                onExpandedChange = { genderDropdownExpanded = it },
                itemToString = {
                    it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
                },
                placeholderText = "Select Gender",
            )

            DateSelectionField(
                label = "Date of Birth",
                date = dob,
                onDateSelected = viewModel::updateDob,
                showPicker = showDobPicker,
                onShowPicker = { showDobPicker = true },
                onDismiss = { showDobPicker = false }
            )

            DateSelectionField(
                label = "Anniversary",
                date = anniversary,
                onDateSelected = { viewModel.updateAnniversary(it) },
                showPicker = showAnniversaryPicker,
                onShowPicker = { showAnniversaryPicker = true },
                onDismiss = { showAnniversaryPicker = false }
            )

            Button(
                onClick = {
                    auth?.let {
                        viewModel.onEvent(
                            CreateProfile.Event.CreateProfile(
                                CreateProfileInput(
                                    name = name,
                                    imageUrl = imageUrl,
                                    dob = dob,
                                    anniversary = anniversary,
                                    gender = gender,
                                    authId = it.id
                                )
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = "Create Profile",
                    style = AppTheme.typography.titleMedium
                )
            }
        }

        if (uiState.isLoading) {
            FullScreenLoader(text = "Creating your profile...")
        }
    }
}
