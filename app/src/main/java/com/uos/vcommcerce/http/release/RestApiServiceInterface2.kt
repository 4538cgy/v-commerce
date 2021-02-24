package com.uos.vcommcerce.http.release

import com.uos.vcommcerce.http.dto.HttpResponseDTO2
import retrofit2.http.*

interface RestApiServiceInterface2 {


    //유저 생성
    @Headers("Accept: application/json")
    @POST("/api/user?")
    fun createUser(@Body userDTO: HttpResponseDTO2.userDTO, @Query("unique_uid") unique_uid: String) : retrofit2.Call<HttpResponseDTO2.userDTO>


}