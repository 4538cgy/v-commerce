package com.uos.vcommcerce.model

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.uos.vcommcerce.activity.productinformation.ProductInformationActivity

class ProductOptionAddItem(){
    var Name : ObservableField<String> = ObservableField<String>()
    var Price :ObservableField<String> = ObservableField<String>()
    var Count :ObservableField<String> = ObservableField<String>()
    var Visible = MutableLiveData<String>("GONE")

    fun ShowImg(view: View){
        Visible.value = "VISIBLE"
        ProductInformationActivity.ProductOptionAdapter.notifyDataSetChanged()
    }
}