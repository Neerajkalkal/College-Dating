package com.example.collegedating.screens.detailscollection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.ui.theme.primary

@Composable
fun EnableNotification(navController: NavHostController) {

    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp
    val screenWidth = localConfig.screenWidthDp

    val isVisible = remember {
        mutableStateOf(false)
    }


    val dpOffset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-360).dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    LaunchedEffect(Unit) {
        isVisible.value = true
    }
    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .offset(x = dpOffset)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = (screenWidth / 10).dp, end = (screenWidth / 10).dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 12).dp))

                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            navController.navigate(Screen.MainHomeScreen.name)
                        },
                    color = primary

                )

                Spacer(modifier = Modifier.height((screenHeight / 8).dp))

                Image(
                    painter = painterResource(id = R.drawable.notifications),
                    contentDescription = "",
                    modifier = Modifier.size((screenHeight / 3).dp)
                )

                Spacer(modifier = Modifier.height((screenHeight / 58).dp))

                Text(
                    text = "Enable notification’s",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height((screenHeight / 58).dp))

                Text(
                    text = "Get push-notification when you get the match or receive a message.",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )


                Spacer(modifier = Modifier.height((screenHeight / 8).dp))

                CustomButton(
                    color = primary,
                    textColor = Color.White,
                    roundedCornerShape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    text = "I want to be notified",
                    textModifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                ) {

                }

            }
        }
    }
}