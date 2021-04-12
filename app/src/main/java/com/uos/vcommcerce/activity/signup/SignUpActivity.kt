package com.uos.vcommcerce.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.popup.ErrorPopUpActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivitySignUpBinding
import com.uos.vcommcerce.popup.PopupManager
import com.uos.vcommcerce.util.Util
import java.util.regex.Pattern


class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    layoutId = R.layout.activity_sign_up
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitysignup = this@SignUpActivity
    }
    fun nextClick(view : View){
        with(binding) {
            // 입력 이메일 체크
            //TODO 이메일 인증 기능 추가 필요
            if (activitySignUpEdittextEmail.text.toString().isEmpty() || !Util().checkEmail(
                    activitySignUpEdittextEmail.text.toString()
                )
            ) {
                PopupManager(this@SignUpActivity).titleNotiPopup(getString(R.string.invalid_email), getString(R.string.input_wrong_email_address)).show(false)
                return
            }
            // 비밀 번호 입력 체크
            if (!Util().checkPassword(activitySignUpEdittextPassword.text.toString(),activitySignUpEdittextPasswordRe.text.toString())) {
                PopupManager(this@SignUpActivity).titleNotiPopup(getString(R.string.confirm_password_mismatch), getString(R.string.enter_your_password_again)).show(false)
                return
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