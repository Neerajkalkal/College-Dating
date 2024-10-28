package com.example.collegedating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.collegedating.navigation.MainNavigation
import com.example.collegedating.screens.cardScreen.CardSwipeScreen
import com.example.collegedating.tokenmanagement.TokenManagement
import com.example.collegedating.ui.theme.CollegeDatingTheme
import com.example.collegedating.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tokenManagement = TokenManagement(context = this)
        setContent {
            CollegeDatingTheme {
                // creating main viewmodel


              CardSwipeScreen()


              /*  val mainViewModel =
                    MainViewModel(tokenManagement)

                MainNavigation(
                  mainViewModel,
                tokenManagement
                )
*/

            }
        }
    }
}
