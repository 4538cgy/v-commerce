package com.uos.vcommcerce.activity.login.popup

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityPasswordErrorPopUpBinding

class PasswordErrorPopUpActivity : Activity() {

    lateinit var binding : ActivityPasswordErrorPopUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_error_pop_up)
        binding.activitypassworderrorpopup = this@PasswordErrorPopUpActivity


        
    }

    fun mOnClose(view: View){

        finish()
    }

    //화면 전환 효과 삭제
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_OUTSIDE){
            return false
        }
        return true
    }

    
    //뒤로가기 방지
    override fun onBackPressed() {
        return;
    }
}