package com.example.info_6130.repository

import com.example.info_6130.network.UserApi
import com.example.info_6130.utils.Constants
import retrofit2.Retrofit

class BaseRepository (retrofit: Retrofit) {
    private var userApi :UserApi = retrofit.create(UserApi::class.java)

    suspend fun getAllCritics() = userApi.getAllCritics()
    suspend fun getCriticReviews(name:String) = userApi.getCriticReviews(name,Constants.API_KEY)
}