package com.vectorinc.vectormoviesearch.presentation.trending

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Trending(title : String) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(Modifier.fillMaxSize()) {
        Scaffold(drawerElevation = 4.dp) {
            TopBar(title =title)
        }
        
    }
    

}

@Composable
fun TopBar(title : String) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
    }
}
