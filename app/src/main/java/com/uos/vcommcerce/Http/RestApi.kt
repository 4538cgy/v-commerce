package com.uos.vcommcerce.Http

import com.google.gson.Gson
import com.uos.vcommcerce.Model.HttpResponseDTO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    private val restApiServiceInterface : RestApiServiceInterface
    private val baseUrl : String = "http://52.79.209.221:8880"

    init{
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        restApiServiceInterface = retrofit.create(RestApiServiceInterface::class.java)

    }
    
    
    //리스트 호출
    fun getSearchListApi(userId : String): Call<HttpResponseDTO.SearchAllListDTO>{
        return restApiServiceInterface.getList(userId)
    }

    //상세 정보 호출
    fun getDetailApi(productId : String): Call<HttpResponseDTO.DetailDTO>{
        return restApiServiceInterface.getDetail(productId)
    }


}