package com.mtsapps.phoneguardian.domain.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun String.toNumberFormat(): String {
   val regex = "(\\\\+\\\\d( )?)?([-\\\\( ]\\\\d{3}[-\\\\) ])( )?\\\\d{3}-\\\\d{4}\n"
    return regex.toRegex().find(this)?.value ?: this
}

fun String?.nameFromUnknownCaller(): String {
    return if (!this.isNullOrEmpty()) this else "Unknown"
}

fun toTimeFormat(hour: Int, minute: Int): String {
    return if (hour < 10 && minute < 10) {
        "0$hour:0$minute"
    } else if (hour < 10) {
        "0$hour:$minute"
    } else if (minute < 10) {
        return "$hour:0$minute"
    } else {
        "$hour:$minute"
    }
}

fun String?.filterNumber() : String{
    return this?.filterNot { it == '(' || it == ')' || it == '-' || it == '+' || it == ' '}?.trim()
        ?: "NaN"
}
fun Context.isPermissionGranted(name: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this, name
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRationale(name: String): Boolean {
    return shouldShowRequestPermissionRationale(name)
}

fun Context.hasPickMediaPermission(): Boolean {

    return when {
        // If Android Version is Greater than Android Pie!
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> true

        else -> isPermissionGranted(name = READ_EXTERNAL_STORAGE)
    }
}

fun Context.gotoApplicationSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.parse("package:${packageName}")
    })
}


fun SnackbarHostState.showSnackBar(
    message: String? = null,
    action: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short,
    coroutineScope: CoroutineScope,
    onSnackBarAction: () -> Unit = {},
    onSnackBarDismiss: () -> Unit = {},
) {

        coroutineScope.launch {

            when (showSnackbar(
                message = message!!,
                duration = duration,
                actionLabel = action,
                withDismissAction = duration == SnackbarDuration.Indefinite,
            )) {
                SnackbarResult.Dismissed -> onSnackBarDismiss.invoke()
                SnackbarResult.ActionPerformed -> onSnackBarAction.invoke()
            }
        }
}

