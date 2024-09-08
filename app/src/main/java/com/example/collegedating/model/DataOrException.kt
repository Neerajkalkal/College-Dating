package com.example.collegedating.model

import java.lang.Exception

data class DataOrException<T, E: Exception>(
    var data: T? = null,
    var loading:Boolean = false,
    var e: E? = null
)