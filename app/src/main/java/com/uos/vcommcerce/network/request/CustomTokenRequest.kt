package com.uos.vcommcerce.network.request

import com.google.gson.annotations.SerializedName

data class CustomTokenRequest(
    @SerializedName("unique_id")
    val uniqueId: String
)