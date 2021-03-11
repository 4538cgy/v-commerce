package com.uos.vcommcerce.activity.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityProfileRegistBinding
import com.uos.vcommcerce.popup.PopupManager

class ProfileRegistActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileRegistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_regist)
        binding.activityprofileregist = this@ProfileRegistActivity

    }

    fun showPicturePopup(view: View){

        PopupManager(this).pictureOptionPopup(null).show(false)

    }
    fun setBirthDay(view:View){
        PopupManager(this).dataPickerPopup(object : PopupManager.DialogClickCallBackStringListener{
            override fun okBtn(result: String) {
                if(result.isNotEmpty()){
                    binding.birthdaytext = result
                }
            }
        }).show(false)
    }
}