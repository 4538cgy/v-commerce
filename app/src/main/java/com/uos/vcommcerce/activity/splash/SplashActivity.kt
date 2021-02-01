package com.uos.vcommcerce.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.login.LoginActivity
import com.uos.vcommcerce.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    var firebaseRemoteConfig : FirebaseRemoteConfig ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        binding.activitysplash = this@SplashActivity


      firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        firebaseRemoteConfig!!.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig!!.setDefaultsAsync(R.xml.default_config)

        firebaseRemoteConfig!!.fetchAndActivate().addOnCompleteListener { task ->

            if (task.isSuccessful){

            }else{

            }
            session()
        }


    }


    fun Permission(){

    }

    fun session(){

        var check : Boolean = firebaseRemoteConfig!!.getBoolean("check")



        if(check){

            //check가 true면 점검중 메세지 혹은 팝업 

        }else{

            //check가 false면 그냥 넘어감
            
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

    }
}