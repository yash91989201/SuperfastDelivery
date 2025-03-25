package com.example.search.ui.components.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Filter
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.example.common.ui.theme.AppTheme

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        shape = AppTheme.shape.medium,
        textStyle = AppTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                imageVector = Lucide.Search,
                contentDescription = "Search items here",
                tint = AppTheme.colorScheme.scrim,
                modifier = Modifier.size(20.dp)
            )
        },
        placeholder = {
            Text(
                text = "Search items",
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.tertiary,
                fontWeight = FontWeight.SemiBold,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Lucide.Filter,
                contentDescription = "Apply filters",
                tint = AppTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = AppTheme.colorScheme.surfaceContainerLow,
            focusedContainerColor = AppTheme.colorScheme.surfaceContainerHighest,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.25.dp,
                color = AppTheme.colorScheme.outlineVariant,
                shape = AppTheme.shape.small
            )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SearchBarPreview() {
    AppTheme {
        SearchBar(query = "") {}
    }
}