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
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.*
import com.uos.vcommcerce.datamodel.product.ProductClassDTO
import com.uos.vcommcerce.datamodel.product.ProductOptionAddItem
import com.uos.vcommcerce.util.setHeight
import kotlinx.android.synthetic.main.activity_product_information.*

class ProductInformationActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductInformationBinding
    var OptionItemHeight = 184
    var Height = MutableLiveData<Int>(OptionItemHeight)

    companion object {
        lateinit var ProductOptionAdapter :ProductOptionRecyclerViewAdapter
    }

    var ProductClassList: ArrayList<ProductClassDTO> = arrayListOf()
    var ProductOptionList: ArrayList<ProductOptionAddItem> = arrayListOf()



    fun getContext() : Context {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_information)
        binding.productInformationActivity = this
        product_class.adapter = ProductClassRecyclerViewAdapter()
        product_class.layoutManager = LinearLayoutManager(this)

        ProductOptionAdapter = ProductOptionRecyclerViewAdapter()
        product_option.adapter = ProductOptionAdapter
        product_option.layoutManager = LinearLayoutManager(this)
    }

    //상품분류 리사이클러뷰 어댑터
    inner class ProductClassRecyclerViewAdapter() : RecyclerView.Adapter<ProductClassRecyclerViewAdapter.CustomViewHolder>() {

        //기본아이템 추가
        init {
            ProductClassList.add(
                ProductClassDTO(
                    ProductClassList.count()
                )
            )
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = ProductClassItemBinding.inflate(view,parent,false)

            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return ProductClassList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(ProductClassList[position])

        }

        inner class CustomViewHolder(var binding: ProductClassItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item : ProductClassDTO){
                with(binding){
                    productClassItem = item
                    executePendingBindings()
                    //화살표에 임시로 추가기능 추가
                    AddClass.setOnClickListener(View.OnClickListener {
                        ProductClassList.add(
                            ProductClassDTO(
                                ProductClassList.count()
                            )
                        )
                        notifyDataSetChanged()
                    })
                }

            }
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