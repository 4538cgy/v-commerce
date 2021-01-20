package com.uos.vcommcerce.activity.regist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityRegistSellerInfoBinding

class RegistSellerInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistSellerInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_seller_info)
        binding.activityregistsellerinfo = this@RegistSellerInfoActivity
        binding.activityRegistSellerImageviewClose.setOnClickListener { finish() }


    }
}