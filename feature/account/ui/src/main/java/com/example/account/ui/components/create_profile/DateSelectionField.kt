package com.example.account.ui.components.create_profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.Lucide
import com.example.core.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    showPicker: Boolean,
    onShowPicker: () -> Unit,
    onDismiss: () -> Unit
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val datePickerState = rememberDatePickerState()

    val text = date?.format(dateFormatter) ?: "Select $label"

    OutlinedButton(
        onClick = onShowPicker,
        shape = AppTheme.shape.small,
        border = BorderStroke(
            color = AppTheme.colorScheme.outlineVariant,
            width = 1.25.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppTheme.colorScheme.surfaceContainerHighest
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = text,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Lucide.Calendar,
            tint = AppTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(18.dp),
            contentDescription = "Open date picker"
        )
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        val selectedDate = Instant.ofEpochMilli(selectedDateMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                        onDismiss()
                    }
                }) {
                    Text(
                        text = "OK",
                        style = AppTheme.typography.titleMedium
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cancel",
                        style = AppTheme.typography.titleMedium
                    )
                }
            },
            shape = AppTheme.shape.small
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
