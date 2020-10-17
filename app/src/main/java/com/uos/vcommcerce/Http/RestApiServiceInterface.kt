package com.uos.vcommcerce.Http

import com.google.gson.JsonObject
import com.uos.vcommcerce.Model.HttpResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
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
    // Post의 경우 응답은 code 와 Error 시 들어오는 error message 만 들어오기 때문에 Call<Void>로 체크합니다.
    @Multipart
    @POST("/api/v1/sell/upload")
    fun upload(@Part("meta")  meta : RequestBody,
               @Part media : MultipartBody.Part): Call<Void>
}