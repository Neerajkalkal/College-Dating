package com.example.collegedating.components.card_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.collegedating.model.dummy.Card
import com.example.collegedating.ui.theme.primary


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SwipeableCard(
    ScreenHeight: Int,
    list: SnapshotStateList<Card>
) {
    // Track card's position and rotation
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rotation by remember { mutableStateOf(0f) }
    val swipeThreshold = 300f

    Box(modifier = Modifier.padding(top = 30.dp)) {
        // Render the second card in the stack for depth effect
        if (list.size >= 2) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding()
                    .height((ScreenHeight / 1.9).dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(list[1].image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        // Top card with swipe gestures
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height((ScreenHeight / 1.9).dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            if (kotlin.math.abs(offsetX) > swipeThreshold) {
                                list.removeFirstOrNull()
                            }
                            offsetX = 0f
                            offsetY = 0f
                            rotation = 0f
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            rotation = (offsetX / 20f).coerceIn(-20f, 20f)
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
            if (list.isNotEmpty()) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(list[0].image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart),
                        color = Color.Transparent.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                list[0].name,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                            Spacer(Modifier.height(2.dp))
                            FlowRow {
                                list[0].common.forEachIndexed { index, text ->
                                    Text(
                                        text,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (index < list[0].common.size - 1) {
                                        Text(", ")
                                    }
                                }
                            }
                        }
                    }
                    if(offsetX>0){

                        CardActionButton(
                            icon = Icons.Default.Favorite,
                            onClick = {},
                            color = Color.White,
                            modifier = Modifier.padding(30.dp),
                            size = (ScreenHeight / 10).dp,
                            colorOfIcon = primary,
                            modifier1 = Modifier.align(Alignment.Center)
                        )
                    }else if (offsetX<0) {
                        CardActionButton(
                            icon = Icons.Default.Close,
                            onClick = {},
                            color = Color.White,
                            modifier = Modifier.padding(30.dp),
                            size = (ScreenHeight / 10).dp,
                            colorOfIcon = Color(0xffF27121),
                            modifier1 = Modifier.align(Alignment.Center)
                        )
                    }



                }
            }
        }
    }
}







