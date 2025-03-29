package com.example.search.ui.components.home

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Filter
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.example.core.ui.components.TextInput

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextInput(
        value = query,
        onValueChange = onQueryChange,
        placeholderText = "Search items",
        leadingIcon = {
            Icon(
                imageVector = Lucide.Search,
                modifier = Modifier.size(18.dp),
                contentDescription = "Search items here",
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Lucide.Filter,
                modifier = Modifier.size(18.dp),
                contentDescription = "Apply filters"
            )
        }
    )
}