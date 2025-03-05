package com.example.account.ui.components.create_profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.account.domain.model.Gender
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Gray80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    gender: Gender? = null,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onGenderChange: (Gender) -> Unit
) {
    var selectedGender by remember { mutableStateOf(gender) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        TextField(
            value = selectedGender?.name?.lowercase()?.replaceFirstChar { it.uppercase() }
                ?: "Select Gender",
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            shape = AppTheme.shape.medium,
            textStyle = AppTheme.typography.bodyMedium,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedTextColor = AppTheme.colorScheme.tertiary,
                focusedTextColor = AppTheme.colorScheme.tertiary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                .border(
                    width = 1.25.dp,
                    color = AppTheme.colorScheme.outlineVariant,
                    shape = AppTheme.shape.small
                )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            Gender.entries.forEach { gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = gender.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = AppTheme.typography.bodyMedium,
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    onClick = {
                        selectedGender = gender
                        onExpandedChange(false)
                        onGenderChange(gender)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenderDropdownPreview() {
    GenderDropdown(
        expanded = false,
        onExpandedChange = {},
        gender = Gender.MALE,
        onGenderChange = {}
    )
}