package com.example.collegedating.navigation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegedating.screens.IntroScreen
import com.example.collegedating.screens.detailscollection.EnableNotification
import com.example.collegedating.screens.detailscollection.GenderSelection
import com.example.collegedating.screens.detailscollection.ProfileDetails
import com.example.collegedating.screens.detailscollection.UsernameScreen
import com.example.collegedating.screens.detailscollection.YourInterest
import com.example.collegedating.screens.signin.SignInScreen
import com.example.collegedating.screens.signup.OtpScreen
import com.example.collegedating.screens.signup.SignUpWithEmail
import com.example.collegedating.screens.signup.SignupScreen
import com.example.collegedating.tokenmanagement.TokenManagement
import com.example.collegedating.viewmodel.MainViewModel

@Composable
fun MainNavigation(mainViewModel: MainViewModel, tokenManagement: TokenManagement) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val navController = rememberNavController()
    val step = tokenManagement.isNewUser()
    Log.d("token valueee", "$step")
    NavHost(
        navController = navController, startDestination =
        if (!tokenManagement.getAccessToken().isNullOrBlank()) {
            when (step) {
                1 -> Screen.ProfileDetails.name
                2 -> Screen.GenderSelection.name
                3 -> Screen.YourInterests.name
                4 -> Screen.EnableNotification.name
                else -> {
                    Screen.IntroScreen.name
                }
            }
        } else {
            Screen.IntroScreen.name
        }

    ) {

        composable(route = Screen.IntroScreen.name) {
            IntroScreen(navController)

        }

        composable(route = Screen.SignupScreen.name) {
            SignupScreen(navController)
        }

        composable(route = Screen.SignupWithEmail.name) {
            SignUpWithEmail(
                navController = navController,
                mainViewModel = mainViewModel, email
            )
        }

        composable(route = Screen.OtpFillScreen.name) {
            OtpScreen(
                navController = navController,
                mainViewModel,
                email,

                )
        }

        composable(route = Screen.UsernameScreen.name) {
            UsernameScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                tokenManagement
            )
        }
        composable(route = Screen.ProfileDetails.name) {
            ProfileDetails(
                navController, mainViewModel,
                tokenManagement
            )
        }

        composable(route = Screen.GenderSelection.name) {
            GenderSelection(navController)
        }

        composable(route = Screen.EnableNotification.name) {
            EnableNotification(navController)
        }
        composable(route = Screen.YourInterests.name) {
            YourInterest(
                navController,
                mainViewModel
            )
        }
        // sign in screen
        composable(route = Screen.SignInScreen.name) {
            SignInScreen(navController)
        }
        composable(route = Screen.MainHomeScreen.name) {
            HomeNavigation()
        }


    }

}