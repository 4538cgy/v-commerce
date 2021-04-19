package com.uos.vcommcerce.network.request

import com.google.gson.annotations.SerializedName

data class FirebaseTokenRequest(
    @SerializedName("firebase_id_token")
    val firebaseToken: String
)