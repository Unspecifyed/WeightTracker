package com.example.weighttracker

import androidx.lifecycle.LiveData
import androidx.room.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {
    @Insert
    suspend fun insert(weight: Weight)

    @Update
    suspend fun update(weight: Weight)

    @Delete
    suspend fun delete(weight: Weight)

    @Query("SELECT * FROM weights ORDER BY date DESC")
    fun getAllWeights(): Flow<List<Weight>> // Return a Flow here

    @Query("SELECT * FROM weights ORDER BY date DESC")
    fun getAllWeightsLiveData(): LiveData<List<Weight>>
}