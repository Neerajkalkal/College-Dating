package com.example.collegedating.components.card_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.collegedating.R
import com.example.collegedating.components.GifImage
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.ui.theme.textColor
import okhttp3.internal.wait

@Composable
fun NoMoreUserFound(){
    Surface(modifier = Modifier.fillMaxSize().padding(20.dp),
        color = Color.White

        ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            GifImage(
                modifier = Modifier.size(200.dp),
                context = LocalContext.current,
                int = R.drawable.love
            )


            Text("Sorry No More Matches Found",
                style = MaterialTheme.typography.titleLarge,
                color = primary,
                textAlign = TextAlign.Center
                )

            Spacer(modifier = Modifier.padding(30.dp))

            Text(
                "That’s everything for now! Check back soon for new content",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textAlign = TextAlign.Center
            )




        }
    }
}