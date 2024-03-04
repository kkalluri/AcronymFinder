package com.example.acronymfinder.features

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronymfinder.data.model.AcronymResponse
import com.example.acronymfinder.data.remote.DataSource
import com.example.weatherapp.data.remote.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class AcronymViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {


    private val _sf = MutableLiveData<String>()
    val sf: LiveData<String> get() = _sf

    fun updateString(sf: String) {
        _sf.value = sf
    }


    private val _acronymData = MutableLiveData<NetworkResult<List<AcronymResponse>>>()
    val acronymData: LiveData<NetworkResult<List<AcronymResponse>>> = _acronymData


    fun getAcronymBySF(sf: String) {
        viewModelScope.launch {
            _acronymData.value = NetworkResult.Loading(true)
            _acronymData.value = dataSource.getAcronymByString(sf)
        }
    }
}