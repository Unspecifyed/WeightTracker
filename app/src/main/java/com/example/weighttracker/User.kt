package com.example.weighttracker

// User.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generated primary

    val username: String,
    val password: String
)
