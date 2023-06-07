package com.example.info_6130.network

import com.example.info_6130.dataModel.CriticsResponse
import com.example.info_6130.dataModel.reviews.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    companion object{
        const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"
        const val API_KEY = "Ap35APdwsDPirw55AxaVxWAKUdqjkEK7"
    }

    @GET("critics/all.json?api-key=${API_KEY}")
    suspend fun getAllCritics(): Response<CriticsResponse>

    @GET("reviews/search.json?api-key=${API_KEY}")
    suspend fun getCriticReviews(
        @Query("reviewer")searchQuery:String,
    ): Response<ReviewResponse>


}