package com.uos.vcommcerce.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.popup.ErrorPopUpActivity
import com.uos.vcommcerce.databinding.ActivitySignUpBinding
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.activitysignup = this@SignUpActivity


        //취소
        binding.activitySignUpButtonCancel.setOnClickListener {
            finish()
        }

        //다음
        binding.activitySignUpButtonNext.setOnClickListener {
            with(binding) {
                // 입력 이메일 체크
                //TODO 이메일 인증 기능 추가 필요
                if (activitySignUpEdittextEmail.text.toString().isEmpty() || !checkEmail(
                        activitySignUpEdittextEmail.text.toString()
                    )
                ) {
                    val intent = Intent(this@SignUpActivity, ErrorPopUpActivity::class.java)
                    intent.putExtra("title", "잘못 된 이메일")
                    intent.putExtra("content", "이메일 주소를 잘 못 입력 하셨습니다.")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    return@setOnClickListener
                }
                // 비밀 번호 입력 체크
                if (!checkPassword(activitySignUpEdittextPassword.text.toString(),activitySignUpEdittextPasswordRe.text.toString())) {
                    val intent = Intent(this@SignUpActivity, ErrorPopUpActivity::class.java)
                    intent.putExtra("title", "확인 비밀번호 불일치")
                    intent.putExtra("content", "비밀번호를 다시 입력해주세요.")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    return@setOnClickListener
                }
                // 모두 입력 되었따면 프로필로 이동
                val intent = Intent(this@SignUpActivity, ProfileRegistActivity::class.java)
                intent.putExtra("email", activitySignUpEdittextEmail.text.toString())
                intent.putExtra("passwor", activitySignUpEdittextPasswordRe.text.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
        }


    }

    // 이메일 형태 체크
    private fun checkEmail(email: String): Boolean {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
    }
    // 비밀번호 체크 숫자 영문(대소문자) 특수문자 8자 이상
    private fun checkPassword(password: String, passwordRe: String): Boolean {
        if (password.isEmpty() || passwordRe.isEmpty()) {
            return false
        }
        if (password != passwordRe) {
            return false
        }
        if(password.length < 8){
            return  false
        }
        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~@$!%*#?&])[a-z[A-Z][0-9]$@!%*#?&]{8,}$"
        return Pattern.matches(pattern,password)
    }


}