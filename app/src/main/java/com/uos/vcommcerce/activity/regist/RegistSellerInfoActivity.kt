package com.uos.vcommcerce.activity.regist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityRegistSellerInfoBinding

/**
 *  2021.1.23 작성자 박정우
 *  판매자 등록 상세 정보 작성 화면
 */
class RegistSellerInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistSellerInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_seller_info)
        binding.activityregistsellerinfo = this@RegistSellerInfoActivity
        binding.activityRegistSellerImageviewClose.setOnClickListener { finish() }


    }
}