package com.example.info_6130.dataModel

data class Result(
    val bio: String,
    val display_name: String,
    val multimedia: Multimedia? = null,
    val seo_name: String,
    val sort_name: String,
    val status: String
)