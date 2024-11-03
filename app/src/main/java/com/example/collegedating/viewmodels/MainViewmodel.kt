package com.example.collegedating.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.collegedating.model.dummy.Card
import com.example.collegedating.model.message.MessageHomeScreen
import com.example.collegedating.networkrepository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    val repository: MainRepository
) : ViewModel() {
    val preferences = mutableStateOf(false)

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
            name = "Bhavya Sharma jsjsfjsfdsdfsfsfsfsd",
            common = listOf("App Development", "Android Development"),
        ),
        Card(
            image = "https://img.freepik.com/free-photo/medium-shot-beautiful-female-model_23-2148365039.jpg?size=626&ext=jpg&ga=GA1.1.2008272138.1727308800&semt=ais_hybrid",
            name = "Yogesh",
            common = listOf("App Development", "Android Development"),
        ),
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


    val listOFMessage = listOf(
        MessageHomeScreen(
            name = "Mannu",
            lastMessage = "Hello",
            time = "10:00 AM",
            image ="https://img.freepik.com/free-photo/medium-shot-beautiful-female-model_23-2148365039.jpg?size=626&ext=jpg&ga=GA1.1.2008272138.1727308800&semt=ais_hybrid"
        ),
        MessageHomeScreen(
            name = "Alok",
            lastMessage = "Ok Done",
            time = "11:00 AM",
            image ="https://thumbs.dreamstime.com/b/handsom-young-male-model-serious-attitude-16040083.jpg"
    ),

        MessageHomeScreen(
            name = "Ruby",
            lastMessage = "Hello",
            time = "10:00 AM",
            image = "https://img.freepik.com/free-photo/medium-shot-beautiful-female-model_23-2148365039.jpg?size=626&ext=jpg&ga=GA1.1.2008272138.1727308800&semt=ais_hybrid",
            unreadMessages = 3
        ),

        )




}