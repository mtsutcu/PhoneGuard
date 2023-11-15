package com.mtsapps.phoneguardian.presentation.components.bottom_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MyBotttomNavigation(navController: NavController) {
    val context = LocalContext.current
    val items = listOf(
        BottomNavItem.Home(context),
        BottomNavItem.ContactsMain(context),
        BottomNavItem.Categories(context)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { _, bottomNavItem ->
            NavigationBarItem(
                selected = currentRoute == bottomNavItem.route,
                alwaysShowLabel = false,
                label = {
                    Text(text = bottomNavItem.title)
                },
                onClick = {
                          navController.navigate(bottomNavItem.route){
                              navController.graph.startDestinationRoute?.let {route->
                                  popUpTo(route){
                                      saveState = true
                                  }
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                },
                icon = { Icon(imageVector = ImageVector.vectorResource(bottomNavItem.icon), contentDescription ="") })
        }
    }
}


