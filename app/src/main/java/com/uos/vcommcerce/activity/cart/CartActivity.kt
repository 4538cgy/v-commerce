package com.uos.vcommcerce.activity.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.cart.cartrecyclerviewadapter.CartInnerRecyclerViewAdapter
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityCartBinding
import com.uos.vcommcerce.databinding.ItemCartBinding
import com.uos.vcommcerce.datamodel.CartDTO

class CartActivity : BaseActivity<ActivityCartBinding>(
    layoutId = R.layout.activity_cart
) {

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
        binding.activityCartRecyclerList.adapter = CartRecyclerViewAdapter()
        binding.activityCartRecyclerList.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
    }

    inner class CartRecyclerViewAdapter() : RecyclerView.Adapter<CartRecyclerViewAdapter.CartRecyclerViewAdapterViewHolder>(){

        var carts : ArrayList<CartDTO> = arrayListOf()
        var data = listOf<CartDTO>()

        init {

            notifyDataSetChanged()

            data = carts
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewAdapter.CartRecyclerViewAdapterViewHolder {

            val binding = ItemCartBinding.inflate(LayoutInflater.from(binding.root.context),parent,false)
            return CartRecyclerViewAdapterViewHolder(binding)



        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: CartRecyclerViewAdapterViewHolder, position: Int) {
            holder.onBind(data[position])



            holder.binding.itemCartRecyclerProductOption.adapter = CartInnerRecyclerViewAdapter(holder.binding.root.context,
                data[position].productAddOption!!
            )
            holder.binding.itemCartRecyclerProductOption.layoutManager = LinearLayoutManager(holder.binding.root.context,LinearLayoutManager.VERTICAL,false)







        }

        inner class CartRecyclerViewAdapterViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root){
            fun onBind(data: CartDTO){
                binding.itemcart = data
            }
        }


    }
}