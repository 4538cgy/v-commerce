package com.uos.vcommcerce.data.repository

import com.uos.vcommcerce.network.AuthService
import com.uos.vcommcerce.network.request.CustomTokenRequest
import com.uos.vcommcerce.network.request.FirebaseTokenRequest
import com.uos.vcommcerce.network.response.CustomTokenResponse
import com.uos.vcommcerce.network.response.SNSAuthCheckResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val authService: AuthService) :
    MemberRepository {

    override fun createCustomToken(id: String): Flow<CustomTokenResponse> {
        return flow {
            emit(authService.createCustomToken(CustomTokenRequest(uniqueId = id)))
        }
    }

    override fun checkSNSAuth(firebaseToken: String): Flow<SNSAuthCheckResponse> {
        return flow {
            emit(authService.checkSNSAuth(FirebaseTokenRequest(firebaseToken)))
        }
    }
}

interface MemberRepository {

    fun createCustomToken(id: String): Flow<CustomTokenResponse>

    fun checkSNSAuth(firebaseToken: String): Flow<SNSAuthCheckResponse>

}