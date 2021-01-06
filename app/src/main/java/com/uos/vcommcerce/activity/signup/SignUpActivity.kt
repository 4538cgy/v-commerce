package com.uos.vcommcerce.activity.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.ads.interactivemedia.v3.internal.ft
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.signup.fragment.AddUserInfoFragment
import com.uos.vcommcerce.activity.signup.fragment.BlankFragment
import com.uos.vcommcerce.activity.signup.fragment.BlankFragment2
import com.uos.vcommcerce.activity.signup.fragment.CreateNewAccountIDFragment
import com.uos.vcommcerce.databinding.ActivitySignUpBinding
import com.uos.vcommcerce.databinding.ActivityWelcomeBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignUpBinding
 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        binding.activitysignup = this@SignUpActivity

        
        //취소
        binding.activitySignUpButtonCancel.setOnClickListener { 
            finish()
        }
        
        //다음
        binding.activitySignUpButtonNext.setOnClickListener { 
            
        }


    }

   




}