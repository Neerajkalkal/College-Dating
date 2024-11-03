package com.example.collegedating.components.card_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.collegedating.ui.theme.primary

@Composable
fun CardActions(ScreenHeight: Int, ScreenWidth: Int) {

    val calc = ScreenWidth / 20
    val padding1 = (ScreenHeight / 40).dp
    val padding2 = (ScreenHeight / 50).dp

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CardActionButton(
            icon = Icons.Default.Close,
            onClick = {},
            color = Color.White,
            modifier = Modifier.padding(padding1),
            size = (ScreenHeight / 10).dp,
            colorOfIcon = Color(0xffF27121)
        )

        Spacer(
            modifier = Modifier.width(
                (calc).dp
            )
        )

        CardActionButton(
            icon = Icons.Default.Favorite,
            onClick = {},
            color = primary,
            modifier = Modifier.padding(padding2),
            size = (ScreenHeight / 8).dp,
            colorOfIcon = Color.White
        )

        Spacer(
            modifier = Modifier.width(
                (calc).dp
            )
        )
        CardActionButton(
            icon = Icons.Default.Star,
            onClick = {},
            color = Color.White,
            modifier = Modifier.padding((padding1)),
            size = (ScreenHeight / 10).dp,
            colorOfIcon = Color(0xff8A2387)
        )
    }


}

@Composable
fun CardActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier,
    colorOfIcon: Color,
    size: Dp,
    modifier1: Modifier = Modifier
        .size(size)
        .shadow(1.dp, CircleShape)
        .clip(CircleShape)  // Clip to CircleShape
        .clickable { onClick() }
) {

    Surface(
        shape = CircleShape,
        color = color,
        modifier =modifier1,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,  // It's good practice to use null instead of an empty string
            modifier = modifier,
            tint = colorOfIcon
        )
    }


}

