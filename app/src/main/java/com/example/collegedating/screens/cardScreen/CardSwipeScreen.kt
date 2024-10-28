package com.example.collegedating.screens.cardScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.collegedating.R
import com.example.collegedating.components.BackButton
import com.example.collegedating.components.NoMoreUserFound
import com.example.collegedating.components.SmallButton
import com.example.collegedating.model.dummy.Card

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardSwipeScreen() {
    val data = listOf(
        Card(
            image = "https://i.pinimg.com/736x/ee/ec/09/eeec096aad149e9f345c810677f83d9f.jpg",
            name = "Mannu",
            common = listOf("App Development", "Android Development"),
        ),
        Card(
            image = "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8ZmVtYWxlJTIwbW9kZWx8ZW58MHx8MHx8fDA%3D",
            name = "Alok Pandit",
            common = listOf("App Development", "Android Development"),
        ),

        Card(

            image = "https://thumbs.dreamstime.com/b/handsom-young-male-model-serious-attitude-16040083.jpg",
            name = "Bhavya Sharma",
            common = listOf("App Development", "Android Development"),
        ),
        Card(
            image = "https://img.freepik.com/free-photo/medium-shot-beautiful-female-model_23-2148365039.jpg?size=626&ext=jpg&ga=GA1.1.2008272138.1727308800&semt=ais_hybrid",
            name = "Yogesh",
            common = listOf("App Development", "Android Development"),
        )
    )

    val list = remember { mutableStateOf(data.toMutableList()) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                }
            }

            val x = remember { mutableStateOf(0) }

            Text(x.value.toString())
            if (list.value.isNotEmpty()) {
                SwipeableCard(
                    list

                )
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SwipeableCard(
    list: MutableState<MutableList<Card>>
) {


    // Track the card's position and rotation
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rotation by remember { mutableStateOf(0f) }


    // Detect swipe gestures
    Box(
        modifier = Modifier.padding(top = 30.dp)

    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding()
                .height(500.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            // Your card content here
            if (list.value.size >= 2) {
                Image(
                    painter = rememberAsyncImagePainter(list.value[1].image),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }



        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(500.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            val swipeThreshold = 300f
                            when {
                                offsetX > swipeThreshold -> {
                                    // Swipe right

                                    val x = list.value
                                    x.removeAt(0)
                                    list.value = x

                                    offsetX = 0f
                                    offsetY = 0f
                                    rotation = 0f
                                }

                                offsetX < -swipeThreshold -> {
                                    // Swipe left
                                    val x = list.value
                                    x.removeAt(0)
                                    list.value = x

                                    offsetX = 0f
                                    offsetY = 0f
                                    rotation = 0f
                                }

                                else -> {
                                    // Reset position if threshold is not met
                                    offsetX = 0f
                                    offsetY = 0f
                                    rotation = 0f
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (list.value.isNotEmpty()) {

                                change.consume()

                                offsetX += dragAmount.x

                                rotation = (offsetX / 30f).coerceIn(
                                    -20f,
                                    20f
                                )


                            }
                        }
                    )
                }
                .graphicsLayer(
                    translationX = offsetX,
                    translationY = offsetY,
                    rotationZ = rotation
                ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            // Replace this with your image or other content


            if (list.value.isNotEmpty()) {

                Box(

                ) {
                    Image(
                        painter = painterResource(R.drawable.girl1),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.BottomEnd),
                        color = Color.Transparent.copy(alpha = 0.2f)
                    ) {
                        Column(  modifier = Modifier.padding(20.dp)) {

                            Text(list.value[0].name,
                                style = MaterialTheme.typography.titleLarge
                                )


                            Spacer(Modifier.padding(10.dp))

                            FlowRow(

                            ) {
                                list.value[0].common.forEachIndexed { index, text ->
                                    Text(text)
                                    if (index < list.value[0].common.size - 1) {
                                        Text(", ") // Add comma between items, not after the last one
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

}
