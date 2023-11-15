package com.mtsapps.phoneguardian.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.mtsapps.phoneguardian.MainActivity
import com.mtsapps.phoneguardian.domain.utils.findActivity
import com.mtsapps.phoneguardian.navigation.NavGraphBottomNav
import com.mtsapps.phoneguardian.presentation.components.bottom_navigation.MyBotttomNavigation

@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()
    val lifecycleOwner = rememberLifecycleEvent()
    val context = LocalContext.current
    LaunchedEffect(key1 = lifecycleOwner){
       when(lifecycleOwner){
           Lifecycle.Event.ON_RESUME-> (context.findActivity() as? MainActivity)?.insertContacts()
           Lifecycle.Event.ON_STOP ->(context.findActivity() as? MainActivity)?.deleteContacts()
           else->{}
       }
    }
    Scaffold(
        bottomBar = { MyBotttomNavigation(navController = bottomNavController) },

        ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            NavGraphBottomNav(navController = bottomNavController)
        }
    }

}
@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}