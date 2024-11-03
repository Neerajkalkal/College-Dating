package com.example.collegedating.components.card_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collegedating.R
import com.example.collegedating.components.BackButton
import com.example.collegedating.components.SmallButton

@Composable
fun TopBar(preferences: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BackButton(
            Modifier.size(30.dp),
            3
        ) {

        }

        Text(
            text = "Discover",
            style = MaterialTheme.typography.titleLarge
        )

        SmallButton(
            Modifier.size(30.dp), image = R.drawable.preferences,
            padding = 4
        ) {
            preferences.value = true
        }
    }
}