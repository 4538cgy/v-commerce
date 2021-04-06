package com.uos.vcommcerce.activity.productinformation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.*
import com.uos.vcommcerce.datamodel.product.ProductOptionAddItem
import com.uos.vcommcerce.util.setHeight
import kotlinx.android.synthetic.main.activity_product_information.*
import kotlinx.android.synthetic.main.product_class_item.view.*

class ProductInformationActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductInformationBinding
    var OptionItemHeight = 184
    var Height = MutableLiveData<Int>(OptionItemHeight)

    companion object {
        lateinit var ProductOptionAdapter :ProductOptionRecyclerViewAdapter
    }

    var ProductClassList: ArrayList<String> = arrayListOf()
    var ProductOptionList: ArrayList<ProductOptionAddItem> = arrayListOf()



    fun getContext() : Context {
        return this
    }
    
    //기본아이템 추가
    init {
        ProductClassList.add("1 번쨰 옵션")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_information)
        binding.productInformationActivity = this
        product_class.adapter = ProductClassRecyclerViewAdapter<String,ProductClassItemBinding>(R.layout.product_class_item,ProductClassList)
        product_class.layoutManager = LinearLayoutManager(this)

        ProductOptionAdapter = ProductOptionRecyclerViewAdapter()
        product_option.adapter = ProductOptionAdapter
        product_option.layoutManager = LinearLayoutManager(this)
    }

    //상품분류 리사이클러뷰 어댑터
    inner class ProductClassRecyclerViewAdapter<item:String,viewBinding : ProductClassItemBinding>(layoutId : Int,ProductClassList : ArrayList<item>) : BaseRecyclerAdapter<item,viewBinding>(layoutId,ProductClassList){

        override fun onBindViewHolder(holder: BaseRecyclerAdapter<item, viewBinding>.CustomViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            holder.itemView. AddClass.setOnClickListener(View.OnClickListener {
                ProductClassList.add( (itemCount+1).toString() + "번쨰 옵션")
                notifyDataSetChanged()
            })
        }
    }


    //옵션 추가 리사이클러뷰 어댑터
    inner class ProductOptionRecyclerViewAdapter() : RecyclerView.Adapter<ProductOptionRecyclerViewAdapter.CustomViewHolder>() {

        //최초1개 아이템 추가
        init {
            ProductOptionList.add(ProductOptionAddItem())
            notifyDataSetChanged()
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = ProductOptionAddItemBinding.inflate(view,parent,false)
            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return ProductOptionList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(ProductOptionList[position])
        }

        inner class CustomViewHolder(var binding: ProductOptionAddItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ProductOptionAddItem) {
                with(binding) {
                    productOptionAddItem = item
                }
                binding
            }
        }
    }

    fun addItem(view:View){
        ProductOptionList.add(ProductOptionAddItem())
        ProductOptionAdapter.notifyDataSetChanged()
        product_option.setHeight(OptionItemHeight*ProductOptionAdapter.itemCount)
    }

    fun activityfinish(view:View){
        finish()
    }
}