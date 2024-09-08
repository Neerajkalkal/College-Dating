package com.example.collegedating.screens.signup

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.screens.loadingscreen.LoadingScreen
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.viewmodel.MainViewModel

@Composable
fun SignUpWithEmail(
    navController: NavController,
    mainViewModel: MainViewModel,
    email: MutableState<String>
) {

    val localConfiguration = LocalConfiguration.current
    val screenHeight = localConfiguration.screenHeightDp
    val screenWidth = localConfiguration.screenWidthDp

    // state of otpsending
    val otpState = mainViewModel.otpRequestState.collectAsState()

    // error
    val error = remember {
        mutableStateOf("")
    }

    otpState.value.e?.let {
        error.value = it.message.toString()
    }


    otpState.value.data?.let {
        if (email.value == it) {
            navController.navigate(route = Screen.OtpFillScreen.name)
            mainViewModel.reset()
        } else {
            error.value = "Email already Exist"
        }
    }


    Scaffold { padding ->
        var isVisible by remember { mutableStateOf(false) }

        // Start animation when component is first composed
        LaunchedEffect(Unit) {
            isVisible = true
        }

        // Animate offset
        val offset by animateDpAsState(
            targetValue = if (isVisible) 0.dp else (-300).dp,
            animationSpec = tween(durationMillis = 300), label = ""
        )

        Surface(
            modifier = Modifier
                .padding(padding)
                .offset { IntOffset(x = offset.roundToPx(), y = 0) }
                .padding(16.dp) // Adjust padding as needed
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
                    text = "My Email",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Please enter your valid Email Id. We will send you a 6-digit code to verify your account.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W400,

                    )

                Spacer(modifier = Modifier.height((screenHeight / 18).dp))

                if (error.value.isNotEmpty()) {
                    Text(text = error.value, color = Color.Red)
                }

                CustomTextField(
                    text = email,
                    borderColor = Color.LightGray,
                    roundedCornerShape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    placeholder = "Place Your Email",
                    containerColor = Color.Transparent,
                    navController = navController
                )

                Spacer(modifier = Modifier.height((screenHeight / 18).dp))


                if (!otpState.value.loading) {
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
                        if (email.value.contains(".com") && email.value.contains("@"))
                            mainViewModel.sendOtp(
                                emailId = email.value
                            )
                        else {
                            error.value = "Invalid Email"
                        }

                    }
                } else {
                    LoadingScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
                }


            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: MutableState<String>,
    borderColor: Color,
    roundedCornerShape: RoundedCornerShape,
    modifier: Modifier,
    placeholder: String,
    containerColor: Color,
    navController: NavController,
    icon: Int? = null
) {

    val show = remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = modifier,
        shape = roundedCornerShape,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        placeholder = { Text(text = placeholder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),

        trailingIcon = {
            icon?.let {

                Icon(painter = painterResource(id = if (show.value) R.drawable.hide else R.drawable.show),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            show.value = !show.value
                        }
                        .size(20.dp))
            }
        },
        visualTransformation = if (show.value) PasswordVisualTransformation() else VisualTransformation.None


    )


}
