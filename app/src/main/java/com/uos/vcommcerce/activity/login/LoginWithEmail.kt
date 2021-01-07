package com.uos.vcommcerce.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityLoginWithEmailBinding

class LoginWithEmail : AppCompatActivity() {

    lateinit var binding : ActivityLoginWithEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_with_email)
        binding.activityloginwithemail = this@LoginWithEmail

        //이메일 입력
        binding.activityLoginWithEmailEdittextId

        //패스워드 입력
        binding.activityLoginWithEmailEdittextPassword

        //로그인 버튼
        binding.activityLoginWithEmailButtonLogin.setOnClickListener {

        }

        //비밀번호 찾기
        binding.activityLoginWithEmailTextviewFindPassword.setOnClickListener {

        }

        //회원가입
        binding.activityLoginWithEmailTextviewSignup.setOnClickListener {

        }
        

    }
}