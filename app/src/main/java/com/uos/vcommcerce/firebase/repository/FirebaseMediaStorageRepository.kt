package com.uos.vcommcerce.firebase.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

class FirebaseMediaStorageRepository {

    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()



    //유저 프로필 downloadUrl 가져오기
    //downloadUrl 은 Uri.parse(downloadUrl) 로 파싱해서 사용 가능합니다.
    fun getProfileImageDownLoadUrl(uid: String) = callbackFlow<String> {
        val storageReference = db.collection("profileImages").document(uid)
        val eventListener = storageReference.get().addOnCompleteListener {
            if(it.isSuccessful){
                if(it.result != null) {
                    val url = it.result
                    this@callbackFlow.sendBlocking(url?.get("image").toString())
                }
            }
        }.addOnFailureListener {
            println("loadFail")
        }
    }
    
    //업로드된 프로필 사진의 downloadUrl 값이 반환됩니다.
    //downloadUrl 은 Uri.parse(downloadUrl) 로 파싱해서 사용 가능합니다.
    fun addProfileImage(uid: String, imageUrl : Uri) = callbackFlow<String> {
        val storageReference = storage.reference.child("userProfileImages").child(uid)
        val eventListener = storageReference.putFile(imageUrl).continueWithTask {

            return@continueWithTask storageReference.downloadUrl
        }.addOnCompleteListener {
            if(it.result == null){
                return@addOnCompleteListener
            }

            if (it.isSuccessful){
                if(it.result != null){
                    println("photo upload success downloadUrl : $it")
                    var map = HashMap<String,Any>()
                    map["image"] = it.toString()

                    db.collection("profileImages").document(uid).set(map)

                }
            }
        }
    }
}