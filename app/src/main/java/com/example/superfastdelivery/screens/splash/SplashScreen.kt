package com.example.superfastdelivery.screens.splash

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.common.navigation.NavigationSubGraph
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Orange100
import com.example.superfastdelivery.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navHost: NavHostController,
    isLoggedIn: Boolean,
) {
    val context = LocalContext.current

    LaunchedEffect(isLoggedIn) {
        delay(2000)

        val isOnboardingCompleted = withContext(Dispatchers.IO) {
            isOnboardingFinished(context)
        }

        val destination: NavigationSubGraph = when {
            isLoggedIn -> NavigationSubGraph.Search
            isOnboardingCompleted -> NavigationSubGraph.Auth
            else -> NavigationSubGraph.Onboarding
        }

        navHost.navigate(destination) {
            popUpTo(0) { inclusive = true }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash_image),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Superfast Delivery",
            style = AppTheme.typography.displaySmall,
            color = Orange100,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

// ✅ Move SharedPreferences to background thread
private fun isOnboardingFinished(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("finished", false)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(
            navHost = rememberNavController(),
            isLoggedIn = false
        )
    }
}