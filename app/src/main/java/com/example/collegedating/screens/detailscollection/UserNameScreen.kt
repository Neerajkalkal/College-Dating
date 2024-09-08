package com.example.collegedating.screens.detailscollection

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.screens.loadingscreen.LoadingScreen
import com.example.collegedating.screens.signup.CustomTextField
import com.example.collegedating.tokenmanagement.TokenManagement
import com.example.collegedating.tokenmanagement.TokenOperation
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.viewmodel.MainViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    tokenManagement: TokenManagement
) {


    BackHandler {

    }
    val loading = remember {
        mutableStateOf(false)
    }
    val isVisible = remember {
        mutableStateOf(false)
    }

    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    val error = remember {
        mutableStateOf("")
    }

    val sendingState = mainViewModel.usernameSendState.collectAsState()

    sendingState.value.data?.let {
        if (it.accessToken.isNotEmpty()) {
            TokenOperation.saveToken(
                token = it,
                tokenManagement = tokenManagement
            )

            TokenOperation.saveSteps(tokenManagement=tokenManagement, step = 1)
            navController.navigate(route = Screen.ProfileDetails.name)
        } else {
            loading.value = false
            error.value = it.status
        }

    }


    val localConfiguration = LocalConfiguration.current
    val screenHeight = localConfiguration.screenHeightDp
    val screenWidth = localConfiguration.screenWidthDp


    val offset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-360).dp,
        animationSpec = tween(durationMillis = 300, delayMillis = 100), label = ""
    )



    if (sendingState.value.e != null) {
        error.value = sendingState.value.e.toString()
    }

    val username = remember {
        mutableStateOf("")
    }

    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .offset(x = offset)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = (screenWidth / 10).dp, end = (screenWidth / 10).dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 8).dp))

                Text(
                    text = "Username",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Please enter a unique username that will be used for identity.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W400,

                    )


                Spacer(modifier = Modifier.height((screenHeight / 40).dp))

                if (error.value.isNotEmpty()) {
                    loading.value = false
                    Text(
                        text = error.value, modifier = Modifier.padding(10.dp),
                        color = Color.Red
                    )
                }
                TextField(
                    value = username.value,
                    onValueChange = {
                        val regex =
                            "^[a-zA-Z_1-9]*$".toRegex() // Regular expression allowing only letters and underscores
                        if (it.matches(regex)) {
                            username.value = it.lowercase(Locale.ROOT)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent

                    ),
                    placeholder = { Text(text = "Place Your Username") },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),


                    )

                Spacer(modifier = Modifier.height((screenHeight / 45).dp))

                CustomTextField(
                    text = password,
                    borderColor = Color.LightGray,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    placeholder = "Password",
                    containerColor = Color.Transparent,
                    navController = navController,
                    icon = R.drawable.hide
                )

                Spacer(modifier = Modifier.height((screenHeight / 45).dp))

                CustomTextField(
                    text = confirmPassword,
                    borderColor = Color.LightGray,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    placeholder = "Confirm Password",
                    containerColor = Color.Transparent,
                    navController = navController,
                    icon = R.drawable.hide
                )


                Spacer(modifier = Modifier.height((screenHeight / 15).dp))

                if (loading.value) {
                    LoadingScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                else {
                    CustomButton(
                        color = primary,
                        textColor = Color.White,
                        roundedCornerShape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                        text = "Continue",
                        textModifier = Modifier.padding(top = 13.dp, bottom = 13.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    ) {
                        if (username.value.length > 3) {
                            if (confirmPassword.value == password.value) {

                                if (confirmPassword.value.length >= 8) {
                                    loading.value = true
                                    mainViewModel.sendUserNameDetails(
                                        username = username.value,
                                        password = password.value
                                    )
                                } else {
                                    mainViewModel.reset()
                                    error.value = "Password Length should be greater than 8"
                                }
                            } else {
                                mainViewModel.reset()
                                error.value = "Password Mismatch"
                            }
                        } else {
                            mainViewModel.reset()
                            error.value = "Username Length should be greater than 3"
                        }


                    }

                }
            }
        }
    }

}