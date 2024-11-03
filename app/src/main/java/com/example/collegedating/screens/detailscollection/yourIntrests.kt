package com.example.collegedating.screens.detailscollection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collegedating.model.InterestsItem
import com.example.collegedating.navigation.Screen
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.screens.loadingscreen.LoadingScreen
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun YourInterest(navController: NavHostController, mainViewModel: MainViewModel) {
    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp
    val screenWidth = localConfig.screenWidthDp

    val isVisible = remember { mutableStateOf(false) }

    val dpOffset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-360).dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )


    val search = remember { mutableStateOf("") }

    val selected = remember { mutableStateListOf<InterestsItem>() }


    val searched = remember { mutableStateListOf<InterestsItem>()  }

    LaunchedEffect(Unit) {
        isVisible.value = true
        mainViewModel.fetchInterests()
    }

    val state = mainViewModel.getInterests.collectAsState()
    val interests = state.value.data?.toList() ?: emptyList()

    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .offset(x = dpOffset)
        ) {
            Column(
                modifier = Modifier

                    .padding(horizontal = (screenWidth / 10).dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 42).dp))

                Text(
                    text = "Your Interests",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height((screenHeight / 78).dp))

                Text(text = "Select a few of your interests and let everyone know what you’re passionate about.")
                Spacer(modifier = Modifier.height((screenHeight / 58).dp))

                if (!state.value.loading) {


                    TextField(
                        value = search.value,
                        onValueChange = { query ->
                            search.value = query
                            searched.clear()
                            interests.filter {
                                it.name.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }.let { searched.addAll(it) }
                        },
                        modifier = Modifier,
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        placeholder = { Text(text = "#Search") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go, keyboardType = KeyboardType.Email
                        ),
                        keyboardActions = KeyboardActions(onGo = {

                        })
                    )

                    Box(modifier = Modifier) {


                            val displayList = if (search.value.isNotEmpty()) searched else interests
                            LazyColumn(modifier = Modifier.height((screenHeight/1.9).dp)) {
                                items(displayList) { interest ->
                                    SmallSelection(
                                        text = interest,
                                        selectedList = selected
                                    )
                                }
                            }

                        }

                    Spacer(modifier = Modifier.height((screenHeight / 20).dp))

                    CustomButton(
                        color = primary,
                        textColor = Color.White,
                        roundedCornerShape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth(),
                        text = "continue",
                        textModifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    ) {
                        navController.navigate(Screen.EnableNotification.name)
                    }


                } else {
                    LoadingScreen(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 30.dp)
                    )


                }
            }
        }
    }
}

@Composable
fun SmallSelection(
    text: InterestsItem,
    selectedList: SnapshotStateList<InterestsItem>,
) {
    val selected = selectedList.contains(text)
    val backgroundColor = if (selected) primary else Color.Transparent
    val contentColor = if (selected) Color.White else Color.Black
    val border = if (selected) null else BorderStroke(1.dp, Color.LightGray)

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                if (selected) selectedList.remove(text) else selectedList.add(text)
            },
        contentColor = contentColor,
        border = border
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.name, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
