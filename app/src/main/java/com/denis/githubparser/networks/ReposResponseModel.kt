package com.denis.githubparser.networks

import com.google.gson.annotations.SerializedName

data class ReposResponseModel(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("language")
    val language: String? = ""
)