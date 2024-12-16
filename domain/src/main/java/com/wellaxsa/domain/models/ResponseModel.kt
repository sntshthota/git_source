package com.wellaxsa.domain.models

import androidx.annotation.Keep

@Keep
data class Game(
    val description: String? = null,
    val end_date: String? = null,
    val gamerpower_url: String? = null,
    val id: Int = 0,
    val image: String? = null,
    val instructions: String? = null,
    val open_giveaway: String? = null,
    val open_giveaway_url: String? = null,
    val platforms: String? = null,
    val published_date: String? = null,
    val status: String? = null,
    val thumbnail: String? = null,
    val title: String? = null,
    val type: String? = null,
    val users: Int = 0,
    val worth: String? = null
)
