package com.uos.vcommcerce.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.activity.login.LoginActivity
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(
    layoutId = R.layout.activity_welcome
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitywelcome = this@WelcomeActivity

        binding.activityWelcomeButtonLogin.setOnClickListener {
            startActivity(Intent(binding.root.context, LoginActivity::class.java))
        }

        binding.activityWelcomeButtonSignup.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivity(Intent(binding.root.context, SignUpActivity::class.java))
            } else {
                Toast.makeText(
                    binding.root.context,
                    "이미 로그인 중입니다. \n 로그아웃 후 다시 시도해주세요.",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
}