package com.uos.vcommcerce.activity.login

import android.os.Bundle
import android.view.View
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityFindPasswordBinding

class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>(
    layoutId = R.layout.activity_find_password
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityfindpassword = this@FindPasswordActivity
    }

    fun mClickButtonOk(view: View) {
        binding.activityFindPasswordTextviewEmailSend.visibility = View.VISIBLE
    }
}