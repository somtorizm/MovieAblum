package com.vectorinc.vectormoviesearch.presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun Loading(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()

    }
}

@Composable
fun LoadingSwipeClone(){
    Box(Modifier.fillMaxWidth()) {
        Card(Modifier.align(Alignment.Center), backgroundColor = Color.DarkGray, elevation = 3.dp,
                shape = CircleShape
            ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp).padding(5.dp)
                ,
                strokeWidth = 2.dp)
        }

    }

}