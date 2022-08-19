package com.vectorinc.vectormoviesearch.ui.theme


import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

/**
 * This is the minimum amount of calculated contrast for a color to be used on top of the
 * surface color. These values are defined within the WCAG AA guidelines, and we use a value of
 * 3:1 which is the minimum for user-interface components.
 */
const val MinContrastOfPrimaryVsSurface = 3f

/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

val DarkBlue = Color(0xFF060D2E)
val DarkPurple = Color(0xFF2196F3)

val TextWhite = Color(0xFFEEEEEE)
val DarkBlueBlured = Color(0x3EEFF0F3)
val DarkGrayLight = Color(0x5E313235)
val DarkDimLight = Color(0xAB070116)
val Pink = Color(0xFFFF538E)


val Yellow800 = Color(0xFFF29F05)
val Red300 = Color(0xFFEA6D7E)

val JetcasterColors = darkColors(
    primary = Yellow800,
    onPrimary = Color.Black,
    primaryVariant = Yellow800,
    secondary = Yellow800,
    onSecondary = Color.Black,
    error = Red300,
    onError = Color.Black
)


val ShimmerColorShades = listOf(

    DarkBlueBlured,

    DarkDimLight,

    DarkGrayLight

)
