package com.example.search.ui.components.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.example.common.ui.theme.AppTheme
import com.example.search.domain.model.DeliverableCategory
import com.example.search.ui.R
import kotlin.math.absoluteValue

val categoryList = listOf(
    DeliverableCategory("Food", R.drawable.food),
    DeliverableCategory("Grocery", R.drawable.grocery),
    DeliverableCategory("Medicine", R.drawable.medicine),
)

@Composable
fun BrowseCategories() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Browse Categories",
            style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = AppTheme.colorScheme.primary,
        )

        val pagerState = rememberPagerState { categoryList.size }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            CategoryCard(
                category = categoryList[page],
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset =
                            (pagerState.currentPage - page + pagerState.currentPageOffsetFraction).absoluteValue

                        lerp(
                            start = 75.dp,
                            stop = 100.dp,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleY = scale / 100.dp
                        }
                    }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(categoryList.size) { index ->
                val isSelected = pagerState.currentPage == index

                val width by animateDpAsState(
                    targetValue = if (isSelected) 24.dp else 10.dp,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                val color by animateColorAsState(
                    targetValue = if (isSelected) AppTheme.colorScheme.primary else AppTheme.colorScheme.surfaceDim,
                    animationSpec = tween(durationMillis = 500), label = ""
                )

                Box(
                    modifier = Modifier
                        .size(height = 10.dp, width = width)
                        .background(color, shape = AppTheme.shape.extraLarge)
                )

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: DeliverableCategory,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = AppTheme.shape.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
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
                textAlign = TextAlign.Center,
                style = AppTheme.typography.titleSmall.copy(color = AppTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(AppTheme.colorScheme.inverseSurface.copy(alpha = 0.6f))
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
