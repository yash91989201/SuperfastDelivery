package com.example.search.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.models.Auth
import com.example.common.models.Profile
import com.example.common.models.Session
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.state_holder.AuthStateHolder
import com.example.common.state_holder.ProfileStateHolder
import com.example.common.state_holder.SessionStateHolder
import com.example.common.ui.theme.AppTheme
import com.example.search.ui.components.sections.BrowseCategories
import com.example.search.ui.components.sections.Header
import com.example.search.ui.components.sections.SearchBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(modifier: Modifier = Modifier, applicationStateHolder: ApplicationStateHolder) {
    val profile by applicationStateHolder.profileStateHolder.profile.collectAsState()

    Scaffold(modifier = modifier.padding(16.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Header(imageUrl = profile?.imageUrl)
            SearchBar(query = "") { }
            BrowseCategories()
        }
    }
}

class FakeSessionStateHolder : SessionStateHolder {
    override val session: StateFlow<Session?> = MutableStateFlow(null)

    override fun updateSession(session: Session) {
        // No-op
    }

    override fun clearSession() {
        // No-op
    }
}

// Mock implementation of AuthStateHolder
class FakeAuthStateHolder : AuthStateHolder {
    override val auth: StateFlow<Auth?> = MutableStateFlow(null)

    override fun updateAuth(auth: Auth) {
        // No-op
    }

    override fun clearAuth() {
        // No-op
    }
}

// Mock implementation of ProfileStateHolder
class FakeProfileStateHolder : ProfileStateHolder {
    override val profile: StateFlow<Profile?> = MutableStateFlow(null)

    override fun updateProfile(profile: Profile) {
        // No-op
    }

    override fun clearProfile() {
        // No-op
    }
}

// Mock implementation of ApplicationStateHolder
class FakeApplicationStateHolder : ApplicationStateHolder {
    override val sessionStateHolder = FakeSessionStateHolder()
    override val authStateHolder = FakeAuthStateHolder()
    override val profileStateHolder = FakeProfileStateHolder()
}

// Jetpack Compose Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            modifier = Modifier,
            applicationStateHolder = FakeApplicationStateHolder()
        )
    }
}