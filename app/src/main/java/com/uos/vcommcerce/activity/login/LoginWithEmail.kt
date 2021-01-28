package com.uos.vcommcerce.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.login.popup.PasswordErrorPopUpActivity
import com.uos.vcommcerce.activity.signup.SignUpActivity
import com.uos.vcommcerce.databinding.ActivityLoginWithEmailBinding


class LoginWithEmail : AppCompatActivity() {

    lateinit var binding: ActivityLoginWithEmailBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_with_email)
        binding.activityloginwithemail = this@LoginWithEmail

        binding.activityLoginWithEmailEdittextId.addTextChangedListener(EditWatcher())
        binding.activityLoginWithEmailEdittextPassword.addTextChangedListener(EditWatcher())

        binding.activityLoginWithEmailEdittextId.setOnEditorActionListener(EventListener())
        binding.activityLoginWithEmailEdittextPassword.setOnEditorActionListener(EventListener())

        //이메일 입력
        binding.activityLoginWithEmailEdittextId

        //패스워드 입력
        binding.activityLoginWithEmailEdittextPassword

        //로그인 버튼
        binding.activityLoginWithEmailButtonLogin.setOnClickListener {

            loginWithEmail(
                binding.activityLoginWithEmailEdittextId.text.toString(),
                binding.activityLoginWithEmailEdittextPassword.text.toString()
            )

        }

        //비밀번호 찾기
        binding.activityLoginWithEmailTextviewFindPassword.setOnClickListener {
            startActivity(Intent(this,FindPasswordActivity::class.java))
        }

        //회원가입
        binding.activityLoginWithEmailTextviewSignup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        //뒤로가기
        binding.activityLoginWithEmailImagebuttonClose.setOnClickListener {
            finish()
        }


    }

    inner class EditWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (binding.activityLoginWithEmailEdittextId.text.length > 2 && binding.activityLoginWithEmailEdittextPassword.text.length > 2) {
                binding.activityLoginWithEmailButtonLogin.setBackgroundResource(R.drawable.button_background_round_black_16dp)
            } else {
                binding.activityLoginWithEmailButtonLogin.setBackgroundResource(R.drawable.button_background_round_gray_16dp)
            }
        }

    }

    inner class EventListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            //키보드가 내려간경우
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //완료
            } else if (actionId == EditorInfo.IME_ACTION_NEXT) {
                //키보드 유지
                handled = true
            }
            return handled

        }

    }

    private fun loginWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && email != null && password.isNotEmpty() && password != null) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //로그인 성공 state - 0
                        UpdateUI(0)
                    } else {
                        //로그인 실패 state - 1
                        UpdateUI(1)
                    }
                }.addOnFailureListener {
                    UpdateUI(1)
                }.addOnCanceledListener {
                    UpdateUI(2)
                }
        }
    }


    private fun UpdateUI(state: Int) {
        when (state) {
            0 -> {
                //로그인 성공시
            }
            1 -> {
                //로그인 실패시
                val intent = Intent(this, PasswordErrorPopUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
            2 -> {
                //로그인 취소시
            }

        }

    }


}