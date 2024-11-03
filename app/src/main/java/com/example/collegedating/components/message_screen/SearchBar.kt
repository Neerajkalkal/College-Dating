package com.example.collegedating.components.message_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SearchBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
        ,
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Search, contentDescription = "",
                tint = Color.Gray.copy(alpha = 0.5f)
            )

            Text(
                "Search",
                modifier = Modifier.padding(start = 10.dp),
                color = Color.Gray.copy(alpha = 0.5f)
            )

        }

    }

}
