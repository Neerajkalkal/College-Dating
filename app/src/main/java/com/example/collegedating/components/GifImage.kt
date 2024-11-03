package com.example.collegedating.components

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun GifImage(
    modifier: Modifier,
    context: Context,
    int: Int
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()




    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(int).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader

        ), contentDescription = "",
        modifier = modifier
    )
}