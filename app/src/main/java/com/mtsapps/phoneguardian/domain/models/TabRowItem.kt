package com.mtsapps.phoneguardian.domain.models

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title : String,
    val selectedIcon: @Composable ()->Unit,
    val unselectedIcon: @Composable ()->Unit,
    val screen : @Composable () -> Unit,
)
