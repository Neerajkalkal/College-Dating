package com.example.collegedating.screens.cardScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.collegedating.components.card_screen.CardActions
import com.example.collegedating.components.card_screen.NoMoreUserFound
import com.example.collegedating.components.card_screen.Preferences
import com.example.collegedating.components.card_screen.SwipeableCard
import com.example.collegedating.viewmodels.MainViewmodel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardSwipeScreen(
    paddingValues: PaddingValues,
    preferences: MutableState<Boolean>,
    mainViewmodel: MainViewmodel
) {

    val ScreenHeight = LocalConfiguration.current.screenHeightDp
    val ScreenWidth = LocalConfiguration.current.screenWidthDp


    val list = remember { mutableStateListOf(*mainViewmodel.data.toTypedArray()) }



Surface(modifier = Modifier.padding(paddingValues),
    color = Color.White
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (list.isNotEmpty()) {
            SwipeableCard(
                ScreenHeight,
                list
            )

            Spacer(modifier = Modifier.height(height = (ScreenHeight / 20).dp))

            CardActions(ScreenHeight, ScreenWidth)

        } else {
            NoMoreUserFound()
        }
    }

    Preferences(preferences)
}
}


