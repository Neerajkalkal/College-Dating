package com.example.collegedating.components.card_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.collegedating.ui.theme.primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Preferences(preferences: MutableState<Boolean>) {
    if (preferences.value) {
        ModalBottomSheet(
            onDismissRequest = { preferences.value = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "Filters",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Interested in",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(30.dp))

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomSelectionBox(
                            name = "Girls", selected = true,
                            modifier = Modifier.weight(1f)
                        ) {}
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        CustomSelectionBox(
                            name = "Boys", selected = false,
                            modifier = Modifier.weight(1f)
                        ) {}
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        CustomSelectionBox(
                            name = "Both", selected = false,
                            modifier = Modifier.weight(1f)
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun CustomSelectionBox(
    name: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(

        modifier = modifier
            .background(if (selected) primary else Color.White)
            .clickable(onClick = { onClick() }),
    ) {
        Text(
            name,
            color = if (selected) Color.White else Color.Black,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(19.dp)
                .align(Alignment.Center)
        )
    }
}
