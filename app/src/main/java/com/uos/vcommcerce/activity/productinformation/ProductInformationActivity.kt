package com.uos.vcommcerce.activity.productinformation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.videoupload.SelectVideoActivity
import com.uos.vcommcerce.databinding.*
import com.uos.vcommcerce.model.ProductClassDTO
import com.uos.vcommcerce.model.ProductOptionAdd
import com.uos.vcommcerce.model.ProductOptionAddItem
import com.uos.vcommcerce.model.TextDTO
import kotlinx.android.synthetic.main.activity_product_information.*
import kotlinx.android.synthetic.main.activity_upload_video.*
import kotlinx.android.synthetic.main.product_option_add.view.*

class ProductInformationActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductInformationBinding


    var ProductClassList: ArrayList<ProductClassDTO> = arrayListOf()
    var ProductOptionList: ArrayList<ProductOptionAdd> = arrayListOf()


    init {
    }

    fun getContext() : Context {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_information)
        product_class.adapter = ProductClassRecyclerViewAdapter()
        product_class.layoutManager = LinearLayoutManager(this)

        product_option.adapter = ProductOptionRecyclerViewAdapter()
        product_option.layoutManager = LinearLayoutManager(this)
    }

    //상품분류 리사이클러뷰 어댑터
    inner class ProductClassRecyclerViewAdapter() : RecyclerView.Adapter<ProductClassRecyclerViewAdapter.CustomViewHolder>() {

        //기본아이템 추가
        init {
            ProductClassList.add(ProductClassDTO(ProductClassList.count()))
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
                        ProductClassList.add(ProductClassDTO(ProductClassList.count()))
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
            ProductOptionList.add(ProductOptionAdd())
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = ProductOptionAddBinding.inflate(view,parent,false)
            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return ProductOptionList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(ProductOptionList[position])
        }

        inner class CustomViewHolder(var binding: ProductOptionAddBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ProductOptionAdd) {
                with(binding) {
                    productOptionAdd = item
                    optionAddItem.adapter = item.ProductOptionAddRecyclerViewAdapter()
                    optionAddItem.layoutManager = LinearLayoutManager(getContext())

                    //옵션 갯수 추가기능
                   optionNumAddButton.setOnClickListener(View.OnClickListener {
                        ProductOptionList.add(ProductOptionAdd())
                        Log.d("옵션추가!","ProductOptionList" + ProductOptionList.count())

                        notifyDataSetChanged()
                    })
                    executePendingBindings()
                }
                binding
            }
        }


    }
}