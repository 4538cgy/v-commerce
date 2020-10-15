package com.uos.vcommcerce.Http

import com.uos.vcommcerce.Model.HttpResponseDTO
import retrofit2.Call
import retrofit2.http.*

interface RestApiServiceInterface {
    
    //전체 리스트 조회
    @Headers("Accept: application/json")
    @GET("/api/v1/search?")
    fun getList(@Query("uid") userId: String): Call<HttpResponseDTO.SearchAllListDTO>
    
    //세부 사항 조회
    
    //회원 검색
    
    //업로드

}