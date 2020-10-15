package com.uos.vcommcerce.Http

import com.uos.vcommcerce.Model.HttpResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RestApiServiceInterface {
    
    //전체 리스트 조회
    @GET("list")
    fun getList(@QueryMap pid : String, status : Int, thumbnail : String, media : String): Call<HttpResponseDTO.SearchAllListDTO>
    
    //세부 사항 조회
    
    //회원 검색
    
    //업로드

}