package com.example.collegedating.screens.requests

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.collegedating.model.dummy.Card
import com.example.collegedating.viewmodels.MainViewmodel

@Composable
fun RequestScreen(
    padding: PaddingValues,
    mainViewmodel: MainViewmodel
) {
    val gridPadding = 20.dp
    val cardPadding = 10.dp


    Surface(
        modifier = Modifier.padding(padding),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(gridPadding)) {

            Text(
                text = "This is a list of people who have liked you and your matches.",
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Light,

                )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(top = gridPadding)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(cardPadding)
            ) {
                items(mainViewmodel.data) { card ->
                    RequestCard(card = card)
                }
            }
        }
    }
}

@Composable
fun RequestCard(card: Card) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .aspectRatio(1f / 1.45f)
            .clip(RoundedCornerShape(10))
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = card.image),
            contentDescription = card.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )

        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)

            )

            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.5f)

                    )
                    .height(
                        34.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {  }
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp),
                        color = Color.White,

                    )

                    Icon(
                        imageVector = Icons.Default.Favorite, contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {  }
                    )


                }


            }


        }


    }
}
