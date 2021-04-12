package com.uos.vcommcerce.activity.popup

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityErrorPopUpBinding

class ErrorPopUpActivity : BaseActivity<ActivityErrorPopUpBinding>(
    layoutId = R.layout.activity_error_pop_up
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityerrorpopup = this@ErrorPopUpActivity

        intent?.let {
            val title = it.getStringExtra("title") ?: ""
            val content = it.getStringExtra("content") ?: ""
            binding.activityErrorPopUpTextviewTitle.text = title
            binding.activityErrorPopUpTextviewSubtitle.text = content
        }

    }

    fun mOnClose(view: View) {
        finish()
    }

    //화면 전환 효과 삭제
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_OUTSIDE) {
            return false
        }
        return true
    }


    //뒤로가기 방지
    override fun onBackPressed() {
        return;
    }
}