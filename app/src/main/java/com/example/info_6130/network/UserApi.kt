package com.example.info_6130.network

import com.example.info_6130.dataModel.CriticsResponse
import com.example.info_6130.utils.Constants.API_KEY
import com.example.info_6130.dataModel.reviews.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("critics/all.json?api-key=${API_KEY}")
    suspend fun getAllCritics(): Response<CriticsResponse>

    @GET("reviews/search.json")
    suspend fun getCriticReviews(
        @Query("reviewer")searchQuery:String,
        @Query("api-key") apiKey:String,
    ): Response<ReviewResponse>


}