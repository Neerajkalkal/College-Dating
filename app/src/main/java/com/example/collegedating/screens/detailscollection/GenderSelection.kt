package com.example.collegedating.screens.detailscollection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.ui.theme.primary

@Composable
fun GenderSelection(navController: NavHostController) {

    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp
    val screenWidth = localConfig.screenWidthDp

    val isVisible = remember {
        mutableStateOf(false)
    }

    val gender = remember {
        mutableIntStateOf(0)
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
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 12).dp))

                Text(
                    text = "I am a",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height((screenHeight / 8).dp))




                OptionChooser(
                    text = "Male",
                    selected = gender.intValue == 0,
                ) {
                    gender.intValue = 0
                }

                Spacer(modifier = Modifier.height((screenHeight / 38).dp))

                OptionChooser(
                    text = "Female",
                    selected = gender.intValue == 1,
                ) {
                    gender.intValue = 1
                }



                Spacer(modifier = Modifier.height((screenHeight / 2.5).dp))

                CustomButton(
                    color = primary,
                    textColor = Color.White,
                    roundedCornerShape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    text = "Confirm",
                    textModifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                ) {
navController.navigate(Screen.YourInterests.name)
                }


            }

        }
    }

}

@Composable
fun OptionChooser(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) primary else Color.Transparent
    val contentColor = if (selected) Color.White else Color.Black
    val border = if (selected) null else BorderStroke(1.dp, Color.LightGray)

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp),
        border = border,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = text, color = contentColor)
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        }
    }
}
