package com.mtsapps.phoneguardian.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mtsapps.phoneguardian.MainActivity
import com.mtsapps.phoneguardian.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashActivityViewModel : SplashActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //   window.insetsController?.hide(WindowInsets.Type.statusBars())
        //        window.insetsController?.hide(WindowInsets.Type.navigationBars()) WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_splash)
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        window.insetsController?.hide(WindowInsets.Type.navigationBars())
        lifecycleScope.launch {
            splashActivityViewModel.isLoading.observe(this@SplashActivity){isLoading->
                splashActivityViewModel.isFirstOpen.observe(this@SplashActivity){isFirstOpen->
                    if (!isLoading && isFirstOpen != null){
                        val intent  = Intent(this@SplashActivity,MainActivity::class.java)
                        intent.putExtra("isFirstOpen",isFirstOpen)
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(intent)
                            finish()
                        },1500)
                        this.cancel()
                    }
                }
            }
        }
    }
}