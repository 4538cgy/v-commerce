package com.uos.vcommcerce.Util

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.okhttp.*
import com.uos.vcommcerce.Model.PushDTO
import java.io.IOException

class FcmPush {

    var JSON = MediaType.parse("application/json; charset = uft-8")
    var url = "https://fcm.googleapis.com/fcm/send"
    var serverKey = "AAAAVdoY9_E:APA91bH2pSj14789xxA3yHmHRV-CqqVmlc9u07zihmnHCR9urdQAlTJaC4qbzKK5nNbDOPczv0y9N1thoBPxzsp0wYOW1Sv8Z_CQZGukiVVzh96X50G0v0K9ZAvivRTMBzsLSn4lKrs4"
    var gson : Gson?=null
    var okHttpClient : OkHttpClient? =null

    companion object{
        var instance = FcmPush()
    }

    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationUid : String, title : String, message : String){

        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get().addOnCompleteListener {

                task ->
            if (task.isSuccessful) {
                var token = task?.result?.get("pushToken").toString()

                var pushDTO = PushDTO()
                pushDTO.to = token
                pushDTO.notification.title = title
                pushDTO.notification.body = message

                var body = RequestBody.create(JSON,gson?.toJson(pushDTO))
                var request = Request.Builder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization","key="+serverKey)
                    .url(url)
                    .post(body)
                    .build()

                okHttpClient?.newCall(request)?.enqueue(object : Callback {
                    override fun onFailure(request: Request?, e: IOException?) {

                    }

                    override fun onResponse(response: Response?) {
                        println(response?.body()?.string())
                    }


                })
            }
        }

    }


}