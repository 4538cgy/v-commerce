package com.uos.vcommcerce.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityFixUserBinding
import com.uos.vcommcerce.databinding.ActivityUserViewBinding

class FixUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFixUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_user)
        binding.fixuseractivity = this

    }
}