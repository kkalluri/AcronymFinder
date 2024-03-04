package com.example.acronymfinder.data.remote

import com.example.acronymfinder.data.model.AcronymResponse
import com.example.weatherapp.data.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataSource @Inject constructor(private val acronymAPI: AcronymAPI) {


    suspend fun getAcronymByString(sf: String): NetworkResult<List<AcronymResponse>> {

        return withContext(Dispatchers.IO) {

            try {
                val response = acronymAPI.getAcronymByString(sf)
                NetworkResult.Success(response)

            } catch (e: Exception) {
                NetworkResult.Failure("Failed to fetch Acronym")
            }
        }
    }
}