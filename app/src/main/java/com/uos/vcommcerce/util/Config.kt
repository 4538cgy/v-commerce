package com.uos.vcommcerce.util

import android.Manifest


//util package에 있는 PermissionUtil과 성격이 겹치는 것 같습니다.
class Config {
    companion object {
        const val Seller = "Seller"
        const val userInfo = "userInfo"
        const val userData = "userData"
        const val sellerInfo = "sellerInfo"


        const val FLAG_PERM_CAMERA = 98
        const val FLAG_PERM_STORAGE = 99
        const val FLAG_REQ_CAMERA = 101
        const val FLAG_REQ_GALLERY = 102
        const val FLAG_FIX_RESULT = 103
        val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA) //카메라 퍼미션
        val STORAGE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) //외부저장소 권한요청
    }
}