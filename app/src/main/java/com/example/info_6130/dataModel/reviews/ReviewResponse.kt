package com.example.info_6130.dataModel.reviews

data class ReviewResponse(
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)