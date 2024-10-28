package com.example.collegedating.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collegedating.ui.theme.primary

@Composable
fun BackButton(modifier: Modifier,
               padding :Int=10,
    onClick:()->Unit ){
    Surface(
        onClick = {
           onClick()
        },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 0.5.dp, color = Color.LightGray),
        modifier =modifier
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "",
            tint = primary,
            modifier = Modifier.padding(padding.dp)
        )
    }
}
@Composable
fun SmallButton(modifier: Modifier,
               padding :Int=10,
                image:Int,
               onClick:()->Unit ){
    Surface(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 0.5.dp, color = Color.LightGray),
        modifier =modifier
    ) {
        Icon(
           painter = painterResource(image),
            contentDescription = "",
            tint = primary,
            modifier = Modifier.padding(padding.dp)
        )
    }
}

