package com.example.weighttracker

// User.kt
import androidx.room.Entity

@Entity(tableName = "users")
data class User(
    val username: String,
    val password: String
)
