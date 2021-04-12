package com.uos.vcommcerce.http.release

import com.uos.vcommcerce.datamodel.KakaoTestDTO
import com.uos.vcommcerce.http.dto.HttpResponseDTO2
import retrofit2.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiRelease {

    private val APITAG : String = "RestApi"
    private val restApiReleaseServiceInterface : RestApiReleaseServiceInterface
    private val baseUrl : String = "https://www.bogosa-dev.ml"

    init{
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        restApiReleaseServiceInterface = retrofit.create(RestApiReleaseServiceInterface::class.java)
    }

    fun createUser(userDTO:HttpResponseDTO2.userDTO , unique_uid : String): Call<HttpResponseDTO2.userDTO> {
        return restApiReleaseServiceInterface.createUser(userDTO,unique_uid)
    }

    fun test(testDTO: KakaoTestDTO) : Call<HttpResponseDTO2.customTokenDTO>{
        return restApiReleaseServiceInterface.test(testDTO)
    }
}