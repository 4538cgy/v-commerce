package com.uos.vcommcerce.activity.cart

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.ActivityCartBinding
import com.uos.vcommcerce.databinding.ItemCartBinding
import com.uos.vcommcerce.databinding.ItemCartInnerOptionBinding
import com.uos.vcommcerce.datamodel.CartDTO

class CartActivity : BaseActivity<ActivityCartBinding>(layoutId = R.layout.activity_cart) {

    var carts : ArrayList<CartDTO> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitycart = this@CartActivity

        //뒤로가기
        binding.activityCartImagebuttonClose.setOnClickListener {
            finish()
        }
        
        //결제하기
        binding.activityCartButtonPayment.setOnClickListener { 
            //오더 액티비티로 이동
        }

        //리사이클러뷰 연동
        binding.activityCartRecyclerList.adapter = CartRecyclerViewAdapter<CartDTO,ItemCartBinding>(R.layout.item_cart,carts)
        binding.activityCartRecyclerList.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
    }
}

class CartRecyclerViewAdapter<item : CartDTO,viewBinding : ItemCartBinding>(layoutId: Int,itemlist : ArrayList<item>) : BaseRecyclerAdapter<item,viewBinding>(layoutId,itemlist){

    //카트 아이템이 없을때 테스트용 데이터 1개추가한것 실제사용시 제거
    init { if(itemlist.count()==0) { addItem(item = CartDTO(null,null,"test") as item) } }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        Log.d("check",""+itemList[position].productAddOption!!)
        holder.binding.itemCartRecyclerProductOption.adapter = CartInnerRecyclerViewAdapter<CartDTO.CartInnerDTO,ItemCartInnerOptionBinding>(R.layout.item_cart_inner_option ,itemList[position].productAddOption!!)
        holder.binding.itemCartRecyclerProductOption.layoutManager = LinearLayoutManager(holder.binding.root.context,LinearLayoutManager.VERTICAL,false)
    }
}

class CartInnerRecyclerViewAdapter<item : CartDTO.CartInnerDTO,viewBinding : ItemCartInnerOptionBinding>(layoutId: Int, itemlist: ArrayList<item>) :  BaseRecyclerAdapter<item,viewBinding>(layoutId,itemlist){

    //카트 추가 옵션 없을때 테스트용 데이터 1개추가한것 실제사용시 제거
    init { if(itemlist.count()==0) { addItem(item = CartDTO.CartInnerDTO("testoption") as item) } }
}