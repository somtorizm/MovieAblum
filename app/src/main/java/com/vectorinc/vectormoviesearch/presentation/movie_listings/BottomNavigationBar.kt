package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlueBlured

@Composable
fun BottomNavigationBar(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "Back",
            modifier = Modifier
                .background(DarkBlueBlured, RoundedCornerShape(16.dp))
                .padding(10.dp)
                .clickable {

                }


        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Back",
            modifier = Modifier
                .padding(10.dp)
                .clickable {

                }


        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_person_24),
            contentDescription = "Back",
            modifier = Modifier
                .padding(10.dp)
                .clickable {

                }


        )
    }
}