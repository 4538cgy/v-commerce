package com.uos.vcommcerce.activity.oder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityOderBinding

class OrderActivity : AppCompatActivity() {

    lateinit var binding : ActivityOderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_oder)
        binding.activityorder = this@OrderActivity


        //뒤로가기
        binding.activityOrderImagebuttonClose.setOnClickListener {
            finish()
        }

        
        //결제하기
        binding.activityOrderButtonPay.setOnClickListener { 
            startActivity(Intent(binding.root.context, OrderCompleteActivitiy::class.java))
        }
    }
}