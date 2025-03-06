package com.example.search.ui.components.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
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
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Gray80

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
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search items here",
                tint = AppTheme.colorScheme.scrim,
                modifier = Modifier.size(24.dp)
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
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Gray80,
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