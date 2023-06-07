package com.example.info_6130.dataModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CriticDetails(
    val src: String?,
    val bio: String,
    val display_name: String,
):Parcelable