package com.example.capstoneproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    //properties
    private val _points = MutableLiveData<Int>()
    val points: LiveData<Int> = _points


    init {
        _points.value = 0
    }

    fun addPoints(input: Int) {
        _points.value = _points.value?.plus(input)
    }
}