package com.example.weighttracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class WeightViewModel(private val weightDao: WeightDao) : ViewModel() {

    val allWeights: StateFlow<List<Weight>> = weightDao.getAllWeights().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Adjust as needed
        initialValue = emptyList()
    )
    fun addWeight(weight: Weight) {
        viewModelScope.launch {
            weightDao.insert(weight)
        }
    }

    fun updateWeight(weight: Weight) {
        viewModelScope.launch {
            weightDao.update(weight)
        }
    }

    fun deleteWeight(weight: Weight) {
        viewModelScope.launch {
            weightDao.delete(weight)
        }
    }
}