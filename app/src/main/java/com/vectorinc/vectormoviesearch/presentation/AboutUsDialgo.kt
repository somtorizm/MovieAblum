package com.vectorinc.vectormoviesearch.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.res.ResourcesCompat
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R

@Composable
fun AboutUsDialog(onDismiss: () -> Unit, showDialog : Boolean) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss
            },
            title = {
                Text(text = "About Us", color = MaterialTheme.colors.secondary ) },
            text = {
                Column(verticalArrangement = Arrangement.Center) {

                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Privacy and Policy",fontSize = 15.sp  )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(text = "Contact Developer",fontSize = 15.sp  )
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss }) {
                    Text("Close", color = Color.White)
                }
            },
            confirmButton = {
                TextButton(onClick = {}) {
                    Text("Ok", color = Color.White)
                }
            }
        )
    }
}