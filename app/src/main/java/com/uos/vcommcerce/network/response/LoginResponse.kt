package com.uos.vcommcerce.network.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("session_token")
    val sessionToken: String,
    @SerializedName("status")
    val status: Int
)