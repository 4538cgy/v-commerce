package com.uos.vcommcerce.activity.productinformation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.BR
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.*
import com.uos.vcommcerce.datamodel.ProductClassDTO
import com.uos.vcommcerce.datamodel.ProductOptionAddItem
import com.uos.vcommcerce.util.setHeight
import kotlinx.android.synthetic.main.activity_product_information.*

class ProductInformationActivity : BaseActivity<ActivityProductInformationBinding>(layoutId = R.layout.activity_product_information) {

    var OptionItemHeight = 184

    companion object {
        lateinit var ProductOptionAdapter: ProductOptionRecyclerViewAdapter
    }

    var ProductClassList: ArrayList<ProductClassDTO> = arrayListOf()
    var ProductOptionList: ArrayList<ProductOptionAddItem> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.productInformationActivity = this
        product_class.adapter = ProductClassRecyclerViewAdapter(R.layout.product_class_item,ProductClassList)
        product_class.layoutManager = LinearLayoutManager(this)

        ProductOptionAdapter = ProductOptionRecyclerViewAdapter(R.layout.product_option_add_item,ProductOptionList)
        product_option.adapter = ProductOptionAdapter
        product_option.layoutManager = LinearLayoutManager(this)
    }


    fun addItem(view: View) {
        ProductOptionAdapter.addItem()
        product_option.setHeight(OptionItemHeight * ProductOptionAdapter.itemCount)
    }

    fun activityfinish(view: View) {
        finish()
    }
}


//상품분류 리사이클러뷰 어댑터
class ProductClassRecyclerViewAdapter(layoutId: Int,itemlist : ArrayList<ProductClassDTO>) : BaseRecyclerAdapter<ProductClassDTO,ProductClassItemBinding>(layoutId,itemlist){

    //기본아이템 추가
    init { addItem() }

    fun addItem(view: View? = null){
        itemList.add(ProductClassDTO(itemList.count()));
        notifyDataSetChanged()
    }

    //뷰홀더에 데이터 할당
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.adapter = this
    }
}


//옵션 추가 리사이클러뷰 어댑터
class ProductOptionRecyclerViewAdapter(layoutId: Int,itemlist : ArrayList<ProductOptionAddItem>) : BaseRecyclerAdapter<ProductOptionAddItem,ProductOptionAddItemBinding>(layoutId,itemlist){

    //기본아이템 추가
    init { addItem() }

    fun addItem(){
        itemList.add(ProductOptionAddItem());
        notifyDataSetChanged()
    }
}