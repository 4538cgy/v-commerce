package com.uos.vcommcerce.network

import com.uos.vcommcerce.network.request.CustomTokenRequest
import com.uos.vcommcerce.network.request.FirebaseTokenRequest
import com.uos.vcommcerce.network.response.CustomTokenResponse
import com.uos.vcommcerce.network.response.SNSAuthCheckResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/user/auth/nfb")
    suspend fun createCustomToken(
        @Body customRequest: CustomTokenRequest
    ): CustomTokenResponse

    @POST("/api/user/auth/fb")
    suspend fun checkSNSAuth(
        @Body firebaseTokenRequest: FirebaseTokenRequest
    ): SNSAuthCheckResponse
}