package com.uos.vcommcerce.Http

import android.util.Log
import com.google.gson.JsonArray
import com.uos.vcommcerce.Model.HttpResponseDTO
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class RestApi {
    private val TAG : String = "RestApi"
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
    fun getSearchListApi(userId: String): Call<HttpResponseDTO.SearchAllListDTO>{
        return restApiServiceInterface.getList(userId)
    }

    fun uploadApi(uploadContentDTO: HttpResponseDTO.UploadContentDTO, file: File) : Call<Void>{

        val requestFile = RequestBody.create(MediaType.parse( "multipart/form-data"), file)
        val uploadFile = MultipartBody.Part.createFormData("media", file.name, requestFile)
        val metaString = makeUploadContentJson(uploadContentDTO)
        if(metaString == null){
            Log.e(TAG, "metaString null")
        }
        //Categroy 를 만들때 데이터가 "category":"[\"건강기능식품\",\"건강식품\"]" 이렇게 들어오는데 이렇게 들어오면 안돼고 ,"category":["건강기능식품","건강식품"] 이렇게 만들어 져야하여 데이터 수정
       var sendstr =  metaString.toString().replace("\\\"","\"")
        sendstr = sendstr.replace("\"[", "[")
        sendstr = sendstr.replace("]\"","]")
        val meta = RequestBody.create(MediaType.parse("text/plain"), sendstr)


        return restApiServiceInterface.upload(
            meta,
            uploadFile
        )
    }

    private fun makeUploadContentJson(uploadContentDTO: HttpResponseDTO.UploadContentDTO) : JSONObject? {
        try {
            // Create a new instance of a JSONObject
            val jobj = JSONObject()

            // With put you can add a name/value pair to the JSONObject
            jobj.put("token", uploadContentDTO.token)
            jobj.put("uid", uploadContentDTO.uid)
            jobj.put("title", uploadContentDTO.title)
            jobj.put("body", uploadContentDTO.body)
            val jobjarry = JsonArray()
            for( i in uploadContentDTO.category!!.indices){
                val subjobj = JSONObject()

                jobjarry.add(uploadContentDTO.category!![i])
            }
            jobj.put("category", jobjarry)
            return jobj
//            val jobjStr : String = jobj.toString()
//            return  jobjStr.toByteArray(Charsets.UTF_8).toString(Charsets.UTF_8)
        } catch (e: JSONException) {
            Log.e(TAG, "Failed to create JSONObject", e)
            return null
        }
    }
}