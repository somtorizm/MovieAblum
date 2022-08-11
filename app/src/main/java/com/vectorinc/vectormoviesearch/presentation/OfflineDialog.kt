package com.vectorinc.vectormoviesearch.presentation

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R

@Composable
fun OfflineDialog(onRetry: () -> Unit,onBack: () -> Unit,message : String, title: String, navigator: DestinationsNavigator) {
    AlertDialog(
        onDismissRequest = {navigator.popBackStack()},
        title = { Text(text = title) },
        text = { Text(text =  message) },
        dismissButton = {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.back_label),color = Color.White)
            }
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_label),color = Color.White)
            }
        }
    )
}