package com.example.collegedating.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.collegedating.screens.cardScreen.CardSwipeScreen
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collegedating.components.card_screen.TopBar
import com.example.collegedating.components.message_screen.MessageScreenTopBar
import com.example.collegedating.components.profile_screen.ProfileScreenTopBar
import com.example.collegedating.components.request_screen.RequestScreenTopBar
import com.example.collegedating.screens.messageScreen.MessageScreen
import com.example.collegedating.screens.profile_screen.ProfileScreen
import com.example.collegedating.screens.requests.RequestScreen

import com.example.collegedating.viewmodels.MainViewmodel


@Composable
fun HomeNavigation(
    mainViewmodel: MainViewmodel = hiltViewModel()
) {



    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route





    Scaffold(
        containerColor = Color.White,

        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        topBar = {
            when (currentRoute) {
                Screen.CardSwipeScreen.name -> {
                    TopBar(mainViewmodel.preferences)
                }

                Screen.Requests.name -> {
                    RequestScreenTopBar()
                }

                Screen.Messages.name -> {
                    MessageScreenTopBar()
                }

                Screen.ProfileScreen.name -> {
                    ProfileScreenTopBar()
                }

                else -> {}
            }

        },
        bottomBar = {
            HomeNavItem(
                navController,
                currentRoute,

                )
        },

        ) { padding ->


        NavHost(
            navController = navController,
            startDestination = Screen.CardSwipeScreen.name
        ) {
            composable(route = Screen.CardSwipeScreen.name) {
                CardSwipeScreen(
                    padding, mainViewmodel.preferences, mainViewmodel,
                )
            }

            composable(route = Screen.Requests.name) {
                RequestScreen(
                    padding, mainViewmodel,
                )
            }
            composable(route = Screen.Messages.name) {
                MessageScreen(padding,mainViewmodel)
            }
            composable(route = Screen.ProfileScreen.name) {
                ProfileScreen(padding)

            }
        }
    }
}
