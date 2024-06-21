package com.example.weighttracker

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weights")
data class Weight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val weight: Float,
    val date: String
)