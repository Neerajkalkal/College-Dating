package com.example.collegedating.navigation

import android.graphics.Color.alpha
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.collegedating.R
import com.example.collegedating.ui.theme.primary

@Composable
fun HomeNavItem(

    navController: NavController,
    currentRoute: String?,

) {



    NavigationBar (
        containerColor = Color(0xffF5F5F5).copy(alpha = 0.5f)
    ){

        NavigationBarItem(
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = Color.White,
                selectedIndicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFFAAAAAA),
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = currentRoute == Screen.CardSwipeScreen.name,
            onClick = {
                navController.navigate(Screen.CardSwipeScreen.name) {
                    // Pop up to the start destination to avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                    // Restore state when reelecting a previously selected item
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.cards), contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
            }
        )


        NavigationBarItem(
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = Color.White,
                selectedIndicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFFAAAAAA),
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = currentRoute == Screen.Requests.name,
            onClick = {
                navController.navigate(Screen.Requests.name) {
                    // Pop up to the start destination to avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                    // Restore state when reelecting a previously selected item
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)                )
            }
        )


        NavigationBarItem(
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = Color.White,
                selectedIndicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFFAAAAAA),
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = currentRoute == Screen.Messages.name,
            onClick = {
                navController.navigate(Screen.Messages.name) {
                    // Pop up to the start destination to avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                    // Restore state when reelecting a previously selected item
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        )



        NavigationBarItem(
            colors = NavigationBarItemColors(
                selectedIconColor = primary,
                selectedTextColor = Color.White,
                selectedIndicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFFAAAAAA),
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = currentRoute == Screen.ProfileScreen.name,
            onClick = {
                navController.navigate(Screen.ProfileScreen.name) {
                    // Pop up to the start destination to avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                    // Restore state when reelecting a previously selected item
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        )


    }


}