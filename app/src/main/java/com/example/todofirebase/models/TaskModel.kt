package com.example.todofirebase.models


data class Task(
    val id: String? = null,
    val title: String? = null,
    val timestamp: Long? = System.currentTimeMillis(),
    val taskDone: Boolean = false
)
