package com.uos.vcommcerce.datamodel.product

import androidx.lifecycle.MutableLiveData

data class ProductClassDTO(var Num : Int,
    var procudtClass: String?  = null,
    var procudtClassHint: String = (Num+1).toString() + " 번째 옵션"
)