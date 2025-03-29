package com.example.superfastdelivery.screens.onboarding

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(
    onSignInClick: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val pages = listOf<OnboardingModel>(
        OnboardingModel.First,
        OnboardingModel.Second,
        OnboardingModel.Third
    )

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(0.8f)
        ) { index ->
            OnboardingShowcase(
                image = pages[index].image,
                title = pages[index].title,
                description = pages[index].description
            )
        }

        OnboardingIndicator(
            pageSize = pages.size,
            currentPage = pagerState.currentPage,
            selectedColor = MaterialTheme.colorScheme.primary,
            unSelectedColor = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(48.dp))

        if (pagerState.currentPage < pages.size - 1) {
            Button(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.75f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Next",
                    style = AppTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pages.size - 1)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                Text(
                    "Skip",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.tertiary
                )
            }
        } else {
            Button(
                onClick = {
                    onSignInClick()
                    setOnboardingFinished(context)
                },
                modifier = Modifier.fillMaxWidth(0.75f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Sign In",
                    style = AppTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                Text(
                    "Back",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.tertiary
                )
            }
        }


    }
}

private fun setOnboardingFinished(context: Context) {
    val sharedPreferences = context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("finished", true)
    editor.apply()
}