package com.vectorinc.vectormoviesearch.presentation.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.presentation.NavGraphs
import com.vectorinc.vectormoviesearch.presentation.destinations.ShowMoviesDestination
import com.vectorinc.vectormoviesearch.presentation.destinations.SplashScreenDestination
import com.vectorinc.vectormoviesearch.presentation.movie_listings.MoviesListingState
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator){
    val scale = remember{
            Animatable(0f)
    }
    val launched = remember {
        true
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 500,
                easing= {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)

        navigator.navigate(ShowMoviesDestination)







    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = com.vectorinc.vectormoviesearch.R.drawable.creaview_logox),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value),
            contentScale = ContentScale.Crop
        )

    }
}