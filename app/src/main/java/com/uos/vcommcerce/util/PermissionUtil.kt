package com.uos.vcommcerce.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionUtil {
    //카메라 권한 여부 체크
    fun isPermitted(context: Context, permissions: Array<String>): Boolean {

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(context, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}