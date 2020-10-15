package com.uos.vcommcerce.Http

import com.google.gson.Gson
import com.uos.vcommcerce.Model.HttpResponseDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    private val restApiServiceInterface : RestApiServiceInterface
    private val baseUrl : String = "http://hello.com"

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        restApiServiceInterface = retrofit.create(RestApiServiceInterface::class.java)

    }
    
    
    //리스트 호출
    fun getSearchListApi(pid : String, status : Int, thumbnail : String, media : String): Call<HttpResponseDTO.SearchAllListDTO>{
        return restApiServiceInterface.getList(pid, status, thumbnail, media)
    }


}