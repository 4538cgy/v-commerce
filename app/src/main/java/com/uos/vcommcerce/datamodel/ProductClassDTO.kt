package com.uos.vcommcerce.datamodel

import androidx.lifecycle.MutableLiveData
//제거가능 - 석우
data class ProductClassDTO(var Num : Int,
    var procudtClass: String?  = null,
    var procudtClassHint: String = (Num+1).toString() + " 번째 옵션"
)