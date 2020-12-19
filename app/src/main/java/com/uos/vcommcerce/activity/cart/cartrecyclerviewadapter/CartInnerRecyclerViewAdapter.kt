package com.uos.vcommcerce.activity.cart.cartrecyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.databinding.ItemCartBinding
import com.uos.vcommcerce.databinding.ItemCartInnerOptionBinding
import com.uos.vcommcerce.model.CartDTO

class CartInnerRecyclerViewAdapter(context: Context, list: ArrayList<CartDTO.CartInnerDTO>) : RecyclerView.Adapter<CartInnerRecyclerViewAdapter.CartInnerRecyclerViewAdapterViewHolder>(){

    var carts = list
    var data = arrayListOf<CartDTO.CartInnerDTO>()
    var context = context

    init {

        notifyDataSetChanged()

        data = carts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartInnerRecyclerViewAdapter.CartInnerRecyclerViewAdapterViewHolder {

        val binding = ItemCartInnerOptionBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartInnerRecyclerViewAdapterViewHolder(binding)



    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CartInnerRecyclerViewAdapterViewHolder, position: Int) {
        holder.onBind(data[position])








    }

    inner class CartInnerRecyclerViewAdapterViewHolder(val binding: ItemCartInnerOptionBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: CartDTO.CartInnerDTO){
            binding.itemcartinneroption = data
        }
    }


}