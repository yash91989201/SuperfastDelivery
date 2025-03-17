package com.example.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val darkColorScheme = darkColorScheme(
    primary = Orange100,
    onPrimary = Color.White,
    primaryContainer = Orange80,
    onPrimaryContainer = Dark100,
    inversePrimary = Orange60,
    secondary = Yellow100,
    onSecondary = Color.White,
    secondaryContainer = Yellow80,
    onSecondaryContainer = Dark100,
    tertiary = Gray100,
    onTertiary = Color.White,
    tertiaryContainer = Gray80,
    onTertiaryContainer = Dark100,
    background = Dark100,
    onBackground = Color.White,
    surface = Dark80,
    onSurface = Color.White,
    surfaceVariant = Dark50,
    onSurfaceVariant = Gray100,
    surfaceTint = Orange100,
    inverseSurface = Color.White,
    inverseOnSurface = Dark100,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color(0xFF3700B3),
    onErrorContainer = Dark20,
    outline = Gray80,
    outlineVariant = Gray50,
    scrim = Dark100,
    surfaceBright = Dark20,
    surfaceContainer = Dark80,
    surfaceContainerHigh = Dark50,
    surfaceContainerHighest = Dark20,
    surfaceContainerLow = Dark100,
    surfaceContainerLowest = Dark100,
    surfaceDim = Dark100
)

val lightColorScheme = lightColorScheme(
    primary = Orange100,
    onPrimary = Color.White,
    primaryContainer = Orange80,
    onPrimaryContainer = Color.White,
    inversePrimary = Orange60,
    secondary = Yellow100,
    onSecondary = Color.White,
    secondaryContainer = Yellow80,
    onSecondaryContainer = Color.White,
    tertiary = Gray100,
    onTertiary = Color.Black,
    tertiaryContainer = Gray50,
    onTertiaryContainer = Color.Black,
    background = Color.White,
    onBackground = Dark100,
    surface = Color.White,
    onSurface = Dark80,
    surfaceVariant = Gray20,
    onSurfaceVariant = Dark50,
    surfaceTint = Orange100,
    inverseSurface = Dark80,
    inverseOnSurface = Color.White,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color(0xFFFFCDD2),
    onErrorContainer = Color.Black,
    outline = Gray50,
    outlineVariant = Gray20,
    scrim = Dark20,
    surfaceBright = Color.White,
    surfaceContainer = Gray20,
    surfaceContainerHigh = Color.White,
    surfaceContainerHighest = Color.White,
    surfaceContainerLow = Gray50,
    surfaceContainerLowest = Gray20,
    surfaceDim = Gray50
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)

data class Sizes(
    val extraSmall: Dp = 8.dp,
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 80.dp
)

val sizes = Sizes()

val shape = Shapes(
    extraSmall = RoundedCornerShape(sizes.extraSmall),
    small = RoundedCornerShape(sizes.small),
    medium = RoundedCornerShape(sizes.medium),
    large = RoundedCornerShape(sizes.large),
    extraLarge = RoundedCornerShape(sizes.extraLarge)
)

@Composable
fun AppTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (isDarkTheme) lightColorScheme else lightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shape,
        content = content
    )
}

object AppTheme {
    val colorScheme: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable get() = MaterialTheme.typography

    val shape: Shapes
        @Composable get() = MaterialTheme.shapes

    val size: Sizes
        @Composable get() = sizes
}