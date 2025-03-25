package com.example.search.ui.components.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.ui.theme.AppTheme
import com.example.search.domain.model.DeliverableCategory
import com.example.search.ui.R

val categoryList = listOf(
    DeliverableCategory("Food", R.drawable.food),
    DeliverableCategory("Grocery", R.drawable.grocery),
    DeliverableCategory("Medicine", R.drawable.medicine),
)

@Composable
fun BrowseCategories() {
    Column {
        Text(
            text = "Browse Categories",
            style = AppTheme.typography.titleLarge,
            fontSize = AppTheme.typography.headlineSmall.fontSize,
            color = AppTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val pagerState = rememberPagerState { categoryList.size }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) { page ->
                val scale by animateFloatAsState(
                    targetValue = if (page == pagerState.currentPage) 1f else 0.8f,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                ) {
                    CategoryItem(categoryList[page])
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(categoryList.size) { index ->
                val isSelected = pagerState.currentPage == index
                val width by animateDpAsState(
                    targetValue = if (isSelected) 24.dp else 10.dp,
                    animationSpec = tween(durationMillis = 300), label = ""
                )
                val color by animateColorAsState(
                    targetValue = if (isSelected) AppTheme.colorScheme.primary else AppTheme.colorScheme.tertiary,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                Box(
                    modifier = Modifier
                        .size(height = 10.dp, width = width)
                        .background(color, shape = RoundedCornerShape(50))
                )

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(category: DeliverableCategory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = AppTheme.shape.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = category.name,
                style = AppTheme.typography.titleSmall.copy(color = AppTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(AppTheme.colorScheme.scrim.copy(alpha = 0.6f))
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BrowseCategoriesPreview() {
    AppTheme {
        BrowseCategories()
    }
}
