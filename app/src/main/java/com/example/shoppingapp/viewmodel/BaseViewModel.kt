package com.example.shoppingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T>: ViewModel() {
    fun uiState(): LiveData<T> = uiState
    val uiState: MutableLiveData<T> = MutableLiveData()
}