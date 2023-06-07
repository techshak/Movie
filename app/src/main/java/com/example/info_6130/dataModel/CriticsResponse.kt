package com.example.info_6130.dataModel

data class CriticsResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)