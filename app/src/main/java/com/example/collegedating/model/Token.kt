package com.example.collegedating.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val status: String,
    val step: String
)
