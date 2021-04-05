package com.uos.vcommcerce.datamodel

import android.os.Message
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.firebase.repository.FirebaseContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductModel : ViewModel() {

    val repository = FirebaseContentRepository()

    //현재제품
    var product : ObservableField<ProductDTO> = ObservableField(ProductDTO())

    //제품리스트
    var productList :  MutableLiveData<ArrayList<ProductDTO>> = MutableLiveData<ArrayList<ProductDTO>>()

    fun setProduct(position : Int){
        product.set(productList.value!!.get(position))
        Log.d("setProduct", product.get().toString())
    }


    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContentAll().collect{
                productList.postValue(it)
                Log.d("init", productList.toString())
            }
        }
    }
}


