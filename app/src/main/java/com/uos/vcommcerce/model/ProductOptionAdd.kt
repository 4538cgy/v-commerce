package com.uos.vcommcerce.model

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.productinformation.ProductInformationActivity
import com.uos.vcommcerce.databinding.ActivityProductInformationBinding
import com.uos.vcommcerce.databinding.ProductClassItemBinding
import com.uos.vcommcerce.databinding.ProductOptionAddBinding
import com.uos.vcommcerce.databinding.ProductOptionAddItemBinding
import kotlinx.android.synthetic.main.activity_product_information.*

class ProductOptionAdd(){

    var ProductOptionItemList: ArrayList<ProductOptionAddItem> = arrayListOf()


    inner class ProductOptionAddRecyclerViewAdapter() :

        RecyclerView.Adapter<ProductOptionAddRecyclerViewAdapter.CustomViewHolder>() {

        init {
            ProductOptionItemList.add(ProductOptionAddItem())
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = ProductOptionAddItemBinding.inflate(view, parent, false)

            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return ProductOptionItemList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(ProductOptionItemList[position])

        }

        inner class CustomViewHolder(var binding: ProductOptionAddItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ProductOptionAddItem) {
                with(binding) {
                    productOptionAddItem = item
                    executePendingBindings()
                }
                binding
            }
        }

    }
}