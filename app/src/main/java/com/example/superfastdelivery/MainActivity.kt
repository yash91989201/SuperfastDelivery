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
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.AppTheme
import com.example.superfastdelivery.navigation.AppNavigationRoot
import com.example.superfastdelivery.navigation.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationRoutes: NavigationRoutes

    @Inject
    lateinit var applicationStateHolder: ApplicationStateHolder

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                val dataLoaded = applicationStateHolder.sessionStateHolder.isSessionLoaded.value &&
                        applicationStateHolder.profileStateHolder.isProfileLoaded.value

                !dataLoaded
            }
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    AppNavigationRoot(
                        navigator = navigator,
                        navigationRoutes = navigationRoutes,
                        applicationStateHolder = applicationStateHolder,
                    )
                }
            }
        }
    }
}