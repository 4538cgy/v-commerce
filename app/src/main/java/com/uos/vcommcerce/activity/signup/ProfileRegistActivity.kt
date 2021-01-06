package com.uos.vcommcerce.activity.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityProfileRegistBinding

class ProfileRegistActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileRegistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_regist)
        binding.activityprofileregist = this@ProfileRegistActivity


    }
}