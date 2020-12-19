package com.uos.vcommcerce.activity.oder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.databinding.ActivityOderCompleteActivitiyBinding

class OrderCompleteActivitiy : AppCompatActivity() {

    lateinit var binding : ActivityOderCompleteActivitiyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_oder_complete_activitiy)
        binding.activityorderCompleteActivity = this@OrderCompleteActivitiy

        
        //뒤로가기
        binding.activityOrderCompleteActivityImagebuttonClose.setOnClickListener { 
            finish()
        }


        binding.activityOrderCompleteActivityButtonGoMain.setOnClickListener {
            startActivity(Intent(binding.root.context , SettingActivity::class.java))
            finish()
        }
    }
}