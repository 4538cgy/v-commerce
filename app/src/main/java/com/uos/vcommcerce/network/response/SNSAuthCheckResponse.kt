package com.uos.vcommcerce.network.response

import com.google.gson.annotations.SerializedName

data class SNSAuthCheckResponse(
    @SerializedName("custom_token")
    val customToken: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("signed_in")
    val signedIn: Boolean
)