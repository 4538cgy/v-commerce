package com.uos.vcommcerce.datamodel.review

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.uos.vcommcerce.R

//리뷰작성에서 보일 사진및 동영상 아이템
class ReviewUploadItemDTO(var imageUri : Uri?)

//리뷰 작성에서 사진및 동영상 리사이클러뷰의 fotter
class ReviewUploadItemFooterDTO(var activity: Activity) {

    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) //외부저장소 권한요청
    val FLAG_REQ_GALLERY = 102
    val FLAG_PERM_STORAGE = 99

    fun addPicture(view: View) {

        if (isPermitted(STORAGE_PERMISSION)) { // 권한 체크하는 함수
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(activity,intent, FLAG_REQ_GALLERY, Bundle())
        } else {
            ActivityCompat.requestPermissions(activity, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
        }

    }

    fun isPermitted(permissions:Array<String>) : Boolean{
        for (permission in permissions){
            val result = ContextCompat.checkSelfPermission(activity , permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
}