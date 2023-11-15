package com.mtsapps.phoneguardian.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtsapps.phoneguardian.presentation.MainScreen
import com.mtsapps.phoneguardian.presentation.onboarding.OnboardingScreen

@Composable
fun RootNavGraph(navController: NavHostController,isFirstOpen : Boolean) {
    NavHost(navController = navController, startDestination = if (isFirstOpen) Screens.OnboardingScreen.route else Screens.MainScreen.route, route = "root" ){
        composable(Screens.OnboardingScreen.route){
            OnboardingScreen(navController = navController)
        }
        composable(route = Screens.MainScreen.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500))
        }){
            MainScreen()
        }
    }
}