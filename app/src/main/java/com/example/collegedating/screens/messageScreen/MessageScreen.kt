package com.example.collegedating.screens.messageScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.collegedating.components.message_screen.SearchBar
import com.example.collegedating.viewmodels.MainViewmodel

@Composable
fun MessageScreen(padding: PaddingValues, mainViewmodel: MainViewmodel) {

    Surface(
        modifier = Modifier.padding(padding), color = Color.White
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

            // search bar
            SearchBar()

            // chat list
            Spacer(
                modifier = Modifier.height(20.dp),
            )

            Text(
                "Messages",
                fontWeight = FontWeight.Bold,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            )

            Spacer(
                modifier = Modifier.height(
                    10.dp
                ),
            )


        }
    }

}

