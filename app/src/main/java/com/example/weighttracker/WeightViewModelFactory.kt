package com.example.weighttracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeightViewModelFactory(private val weightDao: WeightDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>):T {
        if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeightViewModel(weightDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}