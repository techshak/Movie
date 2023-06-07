package com.example.info_6130.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.info_6130.dataModel.CriticsResponse
import com.example.info_6130.dataModel.reviews.ReviewResponse
import com.example.info_6130.repository.BaseRepository
import com.example.info_6130.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ReviewViewModel (private val baseRepository: BaseRepository):ViewModel(){


    private val _allCritics = MutableLiveData<Resource<CriticsResponse>>()
    val allCritics: LiveData<Resource<CriticsResponse>> = _allCritics

    private val _allCriticMovies = MutableLiveData<Resource<ReviewResponse>>()
    val allCriticMovies: LiveData<Resource<ReviewResponse>> = _allCriticMovies

    fun getCritics () = viewModelScope.launch (Dispatchers.IO){
        _allCritics.postValue(Resource.Loading())
        val critics = baseRepository.getAllCritics()
        _allCritics.postValue(handleCriticList(critics))
    }

    fun getCriticReviews(name:String) = viewModelScope.launch (Dispatchers.IO){
        _allCriticMovies.postValue(Resource.Loading())
        val criticsMovies = baseRepository.getCriticReviews(name)
//        Log.d("VVVVV",criticsMovies.body()?.results.toString())
        _allCriticMovies.postValue(handleCriticMovies(criticsMovies))
    }
private fun handleCriticList(critics: Response<CriticsResponse>): Resource<CriticsResponse> {
    if (critics.isSuccessful){
        critics.body()?.let{post ->
            return Resource.Success(post)
        }
    }
    return Resource.Error(critics.message())
}
    private fun handleCriticMovies(critics: Response<ReviewResponse>): Resource<ReviewResponse> {
        if (critics.isSuccessful){
            critics.body()?.let{post ->
                return Resource.Success(post)
            }
        }
        return Resource.Error(critics.message())
    }
}