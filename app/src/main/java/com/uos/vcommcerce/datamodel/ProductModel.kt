package com.uos.vcommcerce.datamodel

import android.os.Message
import android.util.Log
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
    var product : MutableLiveData<ProductDTO> = MutableLiveData(ProductDTO())

    //제품리스트
    var productList : ArrayList<ProductDTO> = ArrayList()

    lateinit var videoAdapter : MainActivity.VideoAdapter

    fun setProduct(position : Int){
        Log.d("setProduct", "setProduct")
        product.postValue(productList.get(position))
    }


    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContentAll().collect{
                productList.addAll(it)
                product.postValue(productList.get(0))
                Log.d("init", productList.toString())
            }
        }
    }
}


