package com.mtsapps.phoneguardian.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun CustomAlertDialog(openDialog : MutableState<Boolean>,message : String,dismissButtonText : String,confirmButtonText : String,confirmClickListener : ()->Unit) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
           // Text(text = "Title")
        },
        text = {
            Text(text =message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(dismissButtonText)
            }
        }
    )
}