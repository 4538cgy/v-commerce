package com.uos.vcommcerce.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.popup.ErrorPopUpActivity
import com.uos.vcommcerce.databinding.ActivitySignUpBinding


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
                if (activitySignUpEdittextEmail.text.toString().isEmpty() || !checkEmail(activitySignUpEdittextEmail.text.toString())) {
                    val intent = Intent(this@SignUpActivity, ErrorPopUpActivity::class.java)
                    intent.putExtra("title", "잘못 된 이메일")
                    intent.putExtra("content", "이메일 주소를 잘 못 입력 하셨습니다.")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    return@setOnClickListener
                }
                // 비밀 번호 입력 체크
                //TODO 비밀 번호 형태 체크 필요 (몇자리 수 이상, 그리고 비번 형태 이를 서버에서 체크할지 앱에서 체크할지)
                if ((activitySignUpEdittextPassword.text.toString() != activitySignUpEdittextPasswordRe.text.toString())
                    || activitySignUpEdittextPassword.text.isEmpty() || activitySignUpEdittextPasswordRe.text.isEmpty()) {
                    val intent = Intent(this@SignUpActivity, ErrorPopUpActivity::class.java)
                    intent.putExtra("title", "확인 비밀번호 불일치")
                    intent.putExtra("content", "비밀번호를 다시 입력해주세요.")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    return@setOnClickListener
                }
                // 모두 입력 되었따면 프로필로 이동
                val intent = Intent(this@SignUpActivity, ProfileRegistActivity::class.java)
                intent.putExtra("email",activitySignUpEdittextEmail.text.toString())
                intent.putExtra("passwor",activitySignUpEdittextPasswordRe.text.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
        }


    }
    // 이메일 형태 체크
    private fun checkEmail(email: String) : Boolean{
      return  email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
    }


}