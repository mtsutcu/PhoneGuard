package com.mtsapps.phoneguardian.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtsapps.phoneguardian.presentation.categories.CategoriesScreen
import com.mtsapps.phoneguardian.presentation.components.bottom_navigation.BottomNavItem
import com.mtsapps.phoneguardian.presentation.contacts.ContactsMainScreen
import com.mtsapps.phoneguardian.presentation.home.HomeScreen

@Composable
fun NavGraphBottomNav(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController,startDestination= BottomNavItem.Home(context).route)
    {
        composable(BottomNavItem.Home(context).route){
            HomeScreen()
        }
        composable(BottomNavItem.ContactsMain(context).route){
            ContactsMainScreen()
        }
        composable(BottomNavItem.Categories(context).route){
            CategoriesScreen()
        }
    }
}