package com.mtsapps.phoneguardian

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.domain.utils.GetCallLogs
import com.mtsapps.phoneguardian.domain.utils.GetContacts
import com.mtsapps.phoneguardian.navigation.RootNavGraph
import com.mtsapps.phoneguardian.ui.theme.PhoneGuardianTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var _isFirstOpen = true

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        _isFirstOpen = intent.getBooleanExtra("isFirstOpen", true)

        setContent {
            PhoneGuardianTheme {
                Surface {
                    val navController = rememberNavController()
                    TransparentSystemBars()
                    RootNavGraph(navController = navController, _isFirstOpen)
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        mainActivityViewModel.deleteAllCallContact()

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityViewModel.deleteAllCallContact()
    }



    fun insertContacts() {
        mainActivityViewModel.insertCallContacts(getCallContacts())
    }
    fun deleteContacts(){
        mainActivityViewModel.deleteAllCallContact()
    }

    @Composable
    fun TransparentSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        val tonalColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) Color.Transparent else tonalColor,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
    }

    private fun getCallContacts(): List<CallContact> {
        val contact = GetContacts.readContactsWithPhotos(this@MainActivity)
        val incoming = GetCallLogs.readIncomingCalls(this@MainActivity)
        val outgoing = GetCallLogs.readOutgoingCalls(this@MainActivity)
        val missed = GetCallLogs.readMissedCalls(this@MainActivity)
        return contact + incoming + outgoing + missed
    }
}

