package com.example.collegedating.screens.signin

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.screens.signup.CustomTextField
import com.example.collegedating.screens.signup.SignupButton
import com.example.collegedating.ui.theme.primary

@Composable
fun SignInScreen(navController: NavHostController) {



    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val isVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    val offset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-300).dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )


    Scaffold { padding ->
        Surface(modifier = Modifier
            .padding(padding)
            .offset { IntOffset(y = offset.roundToPx(), x = 0) }) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    modifier = Modifier.size(110.dp)
                )


                Spacer(modifier = Modifier.height((screenHeight / 18).dp))
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height((screenHeight / 18).dp))
                CustomTextField(
                    text = username,
                    borderColor = Color.LightGray,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier

                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    placeholder = "Username",
                    containerColor = Color.Transparent,
                    navController = navController
                )
                Spacer(modifier = Modifier.height((screenHeight / 28).dp))


                CustomTextField(
                    text = password,
                    borderColor = Color.LightGray,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier

                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    placeholder = "Password",
                    containerColor = Color.Transparent,
                    navController = navController,
                    icon = 1
                )




                Spacer(modifier = Modifier.height((screenHeight / 18).dp))

                CustomButton(
                    color = primary,
                    textColor = Color.White,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier,
                    text = "Sign in",
                    textModifier = Modifier.padding(
                        start = 40.dp,
                        end = 40.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                ) {
                    navController.navigate(Screen.SignupWithEmail.name)
                }


                Spacer(modifier = Modifier.height((screenHeight / 18).dp))


                // signup with line and text

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = (screenWidth / 9).dp, end = (screenWidth / 9).dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    Text(
                        text = "or sign up with",
                        modifier = Modifier.padding(horizontal = 0.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                }


                // other signup button

                Spacer(modifier = Modifier.height((screenHeight / 30).dp))

                Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                    SignupButton(
                        image = R.drawable.google
                    )

                    SignupButton(image = R.drawable.facebook)
                }
            }

        }
    }
}

