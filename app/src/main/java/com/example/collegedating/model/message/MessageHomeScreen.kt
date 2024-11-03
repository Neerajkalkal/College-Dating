package com.example.collegedating.model.message

data class MessageHomeScreen(
    val name: String,
    val lastMessage: String,
    val time: String,
    val image: String,
    val unreadMessages: Int = 0
)
