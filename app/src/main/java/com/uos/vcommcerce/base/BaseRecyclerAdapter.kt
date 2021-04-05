package com.uos.vcommcerce.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.databinding.ActivityReviewUploadItemBinding
import com.uos.vcommcerce.databinding.ActivityReviewUploadItemFooterBinding
import com.uos.vcommcerce.datamodel.ReviewUploadItemDTO
import com.uos.vcommcerce.datamodel.ReviewUploadItemFooterDTO
import java.util.*
import kotlin.collections.ArrayList


open class BaseRecyclerAdapter<item,viewBinding : ViewDataBinding>( var header: Boolean = false,var footer : Boolean = false ) : RecyclerView.Adapter<BaseRecyclerAdapter<item,viewBinding>.CustomViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    var itemList : ArrayList<item> = arrayListOf();

    //베이스가 될 커스텀 홀더
    open inner class CustomViewHolder(val binding: viewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: item){

        }
    }

    fun addItem(item : item){
        itemList.add(item);
    }


    //여기서 바인딩을 할당
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        if(viewType == TYPE_FOOTER){
            val inflaterView = LayoutInflater.from(parent.context)
            val binding = viewBinding.inflate(inflaterView,parent,false);
            return ReviewUploadItemFooter(binding)
        }else{
            val inflaterView = LayoutInflater.from(parent.context)
            val binding = ActivityReviewUploadItemBinding.inflate(inflaterView,parent,false);
            return ReviewUploadItem(binding)
        }
    }

    //홀더의 뷰타입을 커스텀으로 설정
    override fun getItemViewType(position: Int): Int {
        if(header == true && position == 0){
            return TYPE_HEADER
        }
        if(header == true && footer == true &&position == itemList.count()+1) {
            return TYPE_FOOTER
        }
        if(header == false && footer == true &&position == itemList.count()) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM;
    }

    //footer를 포함하여 +1개 를 반환
    override fun getItemCount(): Int {
        var itemCount : Int = itemList.count();
        if(header == true){
            itemCount += 1
        }
        if(footer == true){
            itemCount += 1
        }
        return itemCount
    }

    //뷰홀더에 데이터 할당
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_ITEM ->{
                var holder = holder as ReviewUploadItem
                holder.onBind(reviewList[position])
            }
            TYPE_FOOTER ->{
                var holder = holder as ReviewUploadItemFooter
                holder.onBind(ReviewUploadItemFooterDTO(this@ReviewUpload))
            }
        }
    }
}
