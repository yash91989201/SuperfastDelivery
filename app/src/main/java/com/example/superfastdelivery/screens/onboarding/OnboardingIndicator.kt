package com.example.superfastdelivery.screens.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.ui.theme.AppTheme

@Composable
fun OnboardingIndicator(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {

    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageSize) {
            val width by animateDpAsState(
                if (it == currentPage) 32.dp else 12.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,

                    ),
                label = "OnboardingIndicator"
            )
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(width)
                    .clip(RoundedCornerShape(50))
                    .background(color = if (it == currentPage) selectedColor else unSelectedColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
private fun OnboardingIndicatorPreview() {
    AppTheme {
        OnboardingIndicator(
            pageSize = 3,
            currentPage = 1,
            selectedColor = AppTheme.colorScheme.primaryContainer,
            unSelectedColor = AppTheme.colorScheme.inversePrimary
        )
    }
}