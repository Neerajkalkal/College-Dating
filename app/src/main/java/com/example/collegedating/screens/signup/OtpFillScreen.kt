package com.example.collegedating.screens.signup


import android.content.Context
import android.os.VibrationEffect
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.loadingscreen.LoadingScreen
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    email: MutableState<String>,

) {

    val localContext = LocalContext.current
    val localConfiguration = LocalConfiguration.current
    val screenHeight = localConfiguration.screenHeightDp
    val screenWidth = localConfiguration.screenWidthDp
    val otp = remember {
        mutableStateOf("")
    }


    val otpVerificationState = mainViewModel.otpVerificationState.collectAsState()
    val vibrator = localContext.getSystemService(Context.VIBRATOR_SERVICE)
    otpVerificationState.value.data?.let {
        if (it == "0") {

            if (vibrator is android.os.Vibrator) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(2000)
                }
                otp.value = ""

            }

            mainViewModel.reset()

        } else {
            mainViewModel.setEmailToken(it)
            navController.navigate(Screen.UsernameScreen.name)
            otp.value = ""
        }
    }



    LaunchedEffect(otp.value) {

        if (otp.value.length == 4) {
            mainViewModel.verifyOtp(
                emailId = email.value,
                otp = otp.value
            )
            delay(300)

        }
    }

    val isVisible = remember {
        mutableStateOf(false)
    }

    val timer = remember {
        mutableIntStateOf(50)
    }
    LaunchedEffect(Unit) {
        timer.intValue--
        isVisible.value = true

    }


    LaunchedEffect(timer.intValue) {
        while (timer.intValue > 0) {
            delay(1000)
            timer.intValue--
        }
    }

    val offset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-300).dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )





    Scaffold { padding ->
        Surface(modifier = Modifier
            .padding(padding)
            .offset {
                IntOffset(x = offset.roundToPx(), y = 0)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // back Button
                Surface(
                    onClick = {
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 0.5.dp, color = Color.LightGray),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = primary,
                        modifier = Modifier.padding(10.dp)
                    )
                }


                // Timer
                Spacer(modifier = Modifier.height((screenHeight / 20).dp))



                Text(
                    text = if (timer.intValue > 9) "00:${timer.intValue}" else "00:0${timer.intValue}",
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(modifier = Modifier.height((screenHeight / 40).dp))
                Text(
                    text = "Type the verification code \n" +
                            "we’ve sent you",
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height((screenHeight / 30).dp))

                OtpField(otp = otp, width = screenWidth)

                Spacer(modifier = Modifier.height((screenHeight / 10).dp))

                if (otpVerificationState.value.loading) {
                    LoadingScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Keypad(
                        otp = otp
                    )
                }
                Spacer(modifier = Modifier.height((screenHeight / 20).dp))
                if (timer.intValue == 0) {
                    Text(text = "Send Again",
                        color = primary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            timer.intValue = 50
                        })
                }
            }

        }
    }

}

@Composable
fun Keypad(otp: MutableState<String>) {

    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(4) { columnIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) { rowIndex ->
                    val content: @Composable () -> Unit = when {
                        columnIndex != 3 -> {
                            val number = columnIndex * 3 + rowIndex + 1
                            {
                                Box(modifier = Modifier
                                    .clickable {
                                        if (otp.value.length <= 3) {
                                            otp.value += number
                                        }
                                    }
                                    .weight(1f),
                                    contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "$number",
                                        modifier = Modifier
                                            .padding(18.dp),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }

                        rowIndex == 0 -> {
                            {
                                Box(
                                    modifier = Modifier
                                        .clickable { }
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(

                                        modifier = Modifier
                                            .padding(18.dp),

                                        )
                                }
                            }
                        }

                        rowIndex == 1 -> {
                            {
                                Box(modifier = Modifier
                                    .clickable {
                                        if (otp.value.length <= 3) {
                                            otp.value += 0
                                        }
                                    }
                                    .weight(1f),
                                    contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "0",
                                        modifier = Modifier
                                            .padding(18.dp),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }

                        else -> {
                            {
                                Box(modifier = Modifier
                                    .clickable {
                                        if (otp.value.isNotEmpty()) {
                                            otp.value = otp.value.dropLast(1)
                                        }
                                    }
                                    .weight(1f),
                                    contentAlignment = Alignment.Center) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.clear),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(18.dp)
                                            .size(25.dp),

                                        )
                                }
                            }
                        }
                    }
                    content()
                }
            }
        }
    }

}


@Composable
fun OtpField(
    otp: MutableState<String>,
    width: Int,
) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val boxSize = (width / 6).dp
        val paddingSize = (width / 20).dp
        val otpLength = otp.value.length

        repeat(4) { index ->
            val isFilled = index < otpLength
            val isActive = index == otpLength

            Box(
                modifier = Modifier
                    .size(boxSize)
                    .background(
                        color = if (isFilled) primary else Color.Transparent,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = when {
                            isFilled -> Color.Transparent
                            isActive -> primary.copy(alpha = 0.5f)
                            else -> Color.LightGray
                        },
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Text(
                    text = if (isFilled) otp.value[index].toString() else "0",
                    color = when {
                        isFilled -> Color.White
                        isActive -> primary.copy(alpha = 0.5f)
                        else -> Color.LightGray
                    },
                    modifier = Modifier
                        .padding(paddingSize)
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
