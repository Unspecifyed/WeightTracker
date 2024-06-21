package com.example.weighttracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Weight::class], version = 2, exportSchema =false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun weightDao(): WeightDao
}