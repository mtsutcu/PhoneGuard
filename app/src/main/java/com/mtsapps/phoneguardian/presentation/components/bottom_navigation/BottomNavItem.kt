package com.mtsapps.phoneguardian.presentation.components.bottom_navigation

import android.content.Context
import com.mtsapps.phoneguardian.R

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route : String
){
    class Home(context: Context) : BottomNavItem(context.getString(R.string.homeText), R.drawable.home_icon, "home_screen")
    class ContactsMain(context: Context) : BottomNavItem(context.getString(R.string.contactsText), R.drawable.contact_phone_icon,"phones_screen")
    class Categories(context: Context) : BottomNavItem(context.getString(R.string.categoriesText),R.drawable.category_icon,"categories_screen")
}
