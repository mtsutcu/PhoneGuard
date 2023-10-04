package com.mtsapps.phoneguardian.domain.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun String.toNumberFormat(): String {
    return when (this.length) {
        8 -> {
            "(" + this.substring(
                0,
                1
            ) + ")" + " " + this.substring(1 until 6) + "-" + this.substring(6 until 8) + "-" + this.substring(
                8 until this.length
            )
        }

        9 -> {
            "(" + this.substring(
                0,
                2
            ) + ")" + " " + this.substring(2 until 6) + "-" + this.substring(6 until 8) + "-" + this.substring(
                8 until this.length
            )
        }

        10 -> {
            "(" + this.substring(
                0,
                3
            ) + ")" + " " + this.substring(3 until 6) + "-" + this.substring(6 until 8) + "-" + this.substring(
                8 until this.length
            )
        }
        11 -> {
            "(" + this.substring(
                0,
                4
            ) + ")" + " " + this.substring(4 until 7) + "-" + this.substring(7 until 9) + "-" + this.substring(
                9 until this.length
            )
        }

        else -> {
            this
        }
    }
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