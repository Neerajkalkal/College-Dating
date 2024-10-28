package com.example.collegedating.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.collegedating.R
import com.example.collegedating.screens.ModelCard

@Composable
fun NoMoreUserFound(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){

            Image(painter = painterResource(R.drawable.date),
                contentDescription = "",
                modifier = Modifier.size(150.dp)
                )


            Text("We’re working hard to find your perfect match!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
                )


            Text("You’re unique, and finding someone just as special takes a bit more time. We’re searching through all the profiles and will let you know as soon as we find the one. Hang tight, and we’ll notify you when your best match is ready!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall)
        }


    }
}