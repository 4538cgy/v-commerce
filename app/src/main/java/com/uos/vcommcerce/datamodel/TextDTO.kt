package com.uos.vcommcerce.datamodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

class TextDTO(var title : String = "Test") : Serializable{
    var selectedNum : String = "";
    var checked : Boolean = false
    var liveVisible = MutableLiveData<String>("INVISIBLE")

    fun Remove(view: View){
        Log.d("제거!",title+" 제거완료!")
        selectedNum  = "";
        checked  = false
        liveVisible.value = "INVISIBLE"
    }

}
