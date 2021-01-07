package com.uos.vcommcerce.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityFindPasswordBinding

class FindPasswordActivity : AppCompatActivity() {

    lateinit var binding : ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_find_password)
        binding.activityfindpassword = this@FindPasswordActivity

    }

    fun mClickButtonOk(view: View){
        binding.activityFindPasswordTextviewEmailSend.visibility = View.VISIBLE
    }
}