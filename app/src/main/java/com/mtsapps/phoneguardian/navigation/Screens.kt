package com.mtsapps.phoneguardian.navigation

sealed class Screens(val route : String) {
    object OnboardingScreen : Screens("onboarding")
    object MainScreen : Screens("main")
}