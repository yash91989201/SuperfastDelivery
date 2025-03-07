package com.example.superfastdelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.ui.theme.AppTheme
import com.example.superfastdelivery.navigation.AppNavigationRoot
import com.example.superfastdelivery.navigation.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//const clientid="88877941192-vfsg71tlfla6npd089rh2hrvklbd856r.apps.googleusercontent.com"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationRoutes: NavigationRoutes

    @Inject
    lateinit var applicationStateHolder: ApplicationStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    AppNavigationRoot(
                        applicationStateHolder = applicationStateHolder,
                        navigationRoutes = navigationRoutes
                    )
                }
            }
        }
    }
}