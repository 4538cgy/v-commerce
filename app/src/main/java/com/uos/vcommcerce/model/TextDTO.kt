package com.uos.vcommcerce.model

import androidx.lifecycle.MutableLiveData
import java.io.Serializable

class TextDTO(var title : String = "Test") : Serializable{
    var selectedNum : String = "";
    var checked : Boolean = false
    var liveVisible = MutableLiveData<Boolean>()
}
