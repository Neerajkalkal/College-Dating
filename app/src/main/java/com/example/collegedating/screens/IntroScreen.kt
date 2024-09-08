package com.example.collegedating.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.R
import com.example.collegedating.navigation.Screen
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.ui.theme.textColor
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(navController: NavHostController) {
    val imageList = listOf(
        R.drawable.boy,
        R.drawable.girl1,
        R.drawable.girl2
    )


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val headerText = listOf("Algorithm", "Matches", "Security")


    val pagerState = rememberPagerState() { imageList.size }


    val mainText = listOf(
        "Users going through a vetting process to ensure you never match with bots.",
        "We match you with people that have a large array of similar interests.",
        "Our developers have prioritized your security and privacy at every step"
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 60.dp), // Padding to show adjacent cards
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2.1).dp)
      ,
            userScrollEnabled = false
            ) { index ->
            val scale = calculateScale(pagerState, index)

            ModelCard(
                imagePainter = painterResource(id = imageList[index]),
                modifier = Modifier
                    .graphicsLayer {
                        scaleY = scale
                    }
            )


        }



        Spacer(modifier = Modifier.height((screenHeight / 20).dp))

        Text(
            text = headerText[pagerState.currentPage],
            color = primary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Surface(
            modifier = Modifier
                .padding(start = 60.dp, end = 60.dp, top = (screenHeight / 40).dp)
                .fillMaxWidth(),
            color = Color.Transparent
        ) {

            Text(
                text = mainText[pagerState.currentPage], textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }


        Spacer(modifier = Modifier.height((screenHeight / 20).dp))



        DotsIndicator(
            selectedColor = primary,
            unSelectedColor = Color.LightGray,
            totalDots = 3,
            dotSize = 10.dp,
            selectedIndex = pagerState.currentPage
        )


        Spacer(modifier = Modifier.height((screenHeight / 20).dp))

        CustomButton(
            color = primary,
            textColor = Color.White,
            roundedCornerShape = RoundedCornerShape(15.dp),
            modifier = Modifier,
            text = "Create an account",
            textModifier = Modifier.padding(
                start = 40.dp,
                end = 40.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge

        ) {

            navController.navigate(Screen.SignupScreen.name)
        }

        Spacer(modifier = Modifier.height((screenHeight / 30).dp))

        val text = buildAnnotatedString {
            append("Already have an account? ")

            val start = length
            append("Sign In")
            val end = length

            addStyle(
                style = SpanStyle(
                    color = primary,
                    fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                    fontWeight = FontWeight.Bold

                ),
                start = start,
                end = end
            )

            addStringAnnotation(
                tag = "SignIn",
                annotation = "signIn",
                start = start,
                end = end
            )

        }


        LaunchedEffect(pagerState) {
            while (true) {
                delay(2000)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }


        ClickableText(text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            onClick = { offset ->
                text.getStringAnnotations(tag = "SignIn", start = offset, end = offset)
                    .firstOrNull()?.let {

                    }

        navController.navigate(Screen.SignInScreen.name)
            })


    }
}

@Composable
fun CustomButton(
    color: Color,
    textColor: Color,
    roundedCornerShape: RoundedCornerShape,
    modifier: Modifier,
    text: String,
    textModifier: Modifier,
    style: TextStyle,
    fontWeight: FontWeight,
    onClick: () -> Unit

) {

    Button(
        onClick = { onClick() },
        shape = roundedCornerShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = color
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = textModifier,
            fontWeight = fontWeight,
            style = style
        )
    }

}

@Composable
fun DotsIndicator(
    selectedColor: Color,
    unSelectedColor: Color,
    totalDots: Int,
    dotSize: Dp,
    selectedIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentWidth()
    ) {

        repeat(
            totalDots
        ) { index ->

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(
                        color = if (index == selectedIndex) selectedColor else unSelectedColor,
                        shape = CircleShape
                    )
            )
        }

    }
}

@Composable
fun ModelCard(imagePainter: Painter, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(2f / 3f)

    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Model Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun calculateScale(pagerState: PagerState, page: Int): Float {
    val pageOffset =
        (pagerState.currentPage - page + pagerState.currentPageOffsetFraction).absoluteValue
    return 1f - (pageOffset.coerceIn(0f, 1f) * 0.2f)
}
