package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vectorinc.vectormoviesearch.data.remote.dto.Result
import com.vectorinc.vectormoviesearch.ui.theme.VectorMovieSearchTheme

@Composable
fun MoviesListingItem(
    modifier: Modifier,
    result: Result
){

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VectorMovieSearchTheme {
       ShowImageItems()
    }
}

@Composable
fun ShowImageItems(){
    Text(text = "Hello")
}