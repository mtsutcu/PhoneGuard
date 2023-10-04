package com.mtsapps.phoneguardian.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtsapps.phoneguardian.presentation.categories.CategoriesScreen
import com.mtsapps.phoneguardian.presentation.components.bottom_navigation.BottomNavItem
import com.mtsapps.phoneguardian.presentation.contacts.ContactsMainScreen
import com.mtsapps.phoneguardian.presentation.home.HomeScreen

@Composable
fun NavGraphBottomNav(navController: NavHostController) {
    NavHost(navController,startDestination= BottomNavItem.Home.route)
    {
        composable(BottomNavItem.Home.route){
            HomeScreen()
        }
        composable(BottomNavItem.ContactsMain.route){
            ContactsMainScreen()
        }
        composable(BottomNavItem.Categories.route){
            CategoriesScreen()
        }
    }
}