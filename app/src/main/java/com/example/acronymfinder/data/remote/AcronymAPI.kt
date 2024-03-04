package com.example.acronymfinder.data.remote

import com.example.acronymfinder.data.model.AcronymResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymAPI {
    @GET("dictionary.py")
    suspend fun getAcronymByString(
        @Query("sf") city: String
    ): List<AcronymResponse>

    companion object {
        const val BASE_URL: String = "https://www.nactem.ac.uk/software/acromine/"
    }
}
