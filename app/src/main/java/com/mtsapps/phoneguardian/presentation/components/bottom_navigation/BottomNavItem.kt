package com.mtsapps.phoneguardian.presentation.components.bottom_navigation

import com.mtsapps.phoneguardian.R

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route : String
){
    object Home : BottomNavItem("Home", R.drawable.home_icon,"home_screen")
    object ContactsMain : BottomNavItem("Contacts", R.drawable.contact_phone_icon,"phones_screen")
    object Categories : BottomNavItem("Categories",R.drawable.category_icon,"categories_screen")
}
