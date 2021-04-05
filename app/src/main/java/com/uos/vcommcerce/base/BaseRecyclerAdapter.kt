package com.uos.vcommcerce.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.BR
import com.uos.vcommcerce.databinding.ItemReviewItemBinding
import kotlin.collections.ArrayList


open class BaseRecyclerAdapter<item,viewBinding : ViewDataBinding>(@LayoutRes val layoutId: Int,itemlist : ArrayList<item>) : RecyclerView.Adapter<BaseRecyclerAdapter<item,viewBinding>.CustomViewHolder>() {

    //각뷰에 결합될 variable name 은 item 이여야함

    var itemList : ArrayList<item> = arrayListOf()

    init {
        itemList = itemlist;
    }


    //베이스가 될 커스텀 홀더
    inner class CustomViewHolder(val binding: viewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item : item){
            binding.setVariable(BR.item,item)
        }
    }

    fun addItem(item : item){
        itemList.add(item);
    }


    //여기서 바인딩을 할당
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding : viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),layoutId,parent,false)
        return CustomViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return itemList.count()
    }

    //뷰홀더에 데이터 할당
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}