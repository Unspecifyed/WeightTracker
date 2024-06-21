package com.example.weighttracker

// UserDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    // Use a more descriptive name for this method
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun authenticateUser(username: String, password: String): User?
    abstract fun getUser(username: String, password: String): Any
}
