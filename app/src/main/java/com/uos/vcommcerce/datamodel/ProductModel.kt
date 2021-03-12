package com.uos.vcommcerce.datamodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uos.vcommcerce.firebase.repository.FirebaseContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductModel : ViewModel() {


    val repository = FirebaseContentRepository()

    //현재제품
    var Product : MutableLiveData<ProductDTO> = MutableLiveData(ProductDTO())

    //제품리스트
    var ProductList : ArrayList<ProductDTO> = ArrayList()

    fun setProduct(position : Int){
        Log.d("setProduct", "setProduct")

        Product.postValue(ProductList.get(position))
    }

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContentAll().collect{
                ProductList.addAll(it)
                Product.postValue(ProductList.get(0))
                Log.d("init", ProductList.toString())

            }
        }
    }
}
