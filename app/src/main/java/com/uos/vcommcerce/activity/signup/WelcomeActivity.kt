package com.uos.vcommcerce.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.LoginActivity
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {


    lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome)
        binding.activitywelcome = this@WelcomeActivity

        binding.activityWelcomeButtonLogin.setOnClickListener {
            startActivity(Intent(binding.root.context,LoginActivity::class.java))
        }

        binding.activityWelcomeButtonSignup.setOnClickListener {
            startActivity(Intent(binding.root.context,SignUpActivity::class.java))
        }
    }
}