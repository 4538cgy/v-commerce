package com.uos.vcommcerce.network.request


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("firebase_id_token")
    val firebaseIdToken: String
)