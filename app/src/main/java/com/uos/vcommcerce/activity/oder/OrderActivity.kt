package com.uos.vcommcerce.activity.oder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.GridData
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityOderBinding
import com.uos.vcommcerce.databinding.FragmentVideoGridBinding
import com.uos.vcommcerce.databinding.ItemPaymentboxBinding
import com.uos.vcommcerce.databinding.RecyclerGridItemBinding
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import kotlinx.android.synthetic.main.activity_oder.*


data class PaymentBtnData(
    var btnName: String
)

class OrderActivity : AppCompatActivity() {
    val paymentBtnDataList = listOf(
        PaymentBtnData("신용카드"),
        PaymentBtnData("가상계좌"),
        PaymentBtnData("계좌이체")
    )

    lateinit var binding : ActivityOderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_oder)
        binding.activityorder = this@OrderActivity

        val paymentAdapter = PaymentBtnAdapter(this)
        binding.recyclerPaymentboxView.layoutManager = GridLayoutManager(this,3)
        binding.recyclerPaymentboxView.adapter = paymentAdapter
        paymentAdapter.data = paymentBtnDataList

        //뒤로가기
        binding.activityOrderImagebuttonClose.setOnClickListener {
            finish()
        }

        //주소검색
        binding.addressSearchBtn.setOnClickListener {

        }

        
        //결제하기
        binding.activityOrderButtonPay.setOnClickListener { 
            startActivity(Intent(binding.root.context, OrderCompleteActivitiy::class.java))
        }
    }

    inner class PaymentBtnViewHolder(val binding : ItemPaymentboxBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: PaymentBtnData){
            binding.paymentbtndata = data
        }
    }

    inner class PaymentBtnAdapter(val context: Context): RecyclerView.Adapter<PaymentBtnViewHolder>(){

        var data = listOf<PaymentBtnData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentBtnViewHolder {
            val binding = ItemPaymentboxBinding.inflate(
                LayoutInflater.from(context), parent, false)

            return PaymentBtnViewHolder(binding)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: PaymentBtnViewHolder, position: Int) {
            holder.onBind(data[position])

            //그리드 버튼 클릭시
            holder.itemView.setOnClickListener{
                Toast.makeText(context, data[position].btnName, Toast.LENGTH_SHORT).show()

                //.val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
                //vedioIntent.putExtra("btnName", data[position].btnName)
                //startActivity(vedioIntent)

            }
        }
    }



}