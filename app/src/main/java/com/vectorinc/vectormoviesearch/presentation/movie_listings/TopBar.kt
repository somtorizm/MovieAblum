package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlueBlured

@Composable
fun TopBar(
    name: String,
    modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()

    ) {

        Image(
            painter = painterResource(id = R.drawable.creaview_logox),
            contentDescription = null,
            Modifier
                .weight(1f)
                .height(50.dp)
                .fillMaxWidth(1f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Back",
            modifier = Modifier
                .background(DarkBlueBlured, RoundedCornerShape(16.dp))
                .padding(10.dp)
                .clickable {

                }


        )


    }


}