package com.uos.vcommcerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class SplashActivity : AppCompatActivity() {

    var firebaseRemoteConfig : FirebaseRemoteConfig ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseRemoteConfig = Firebase.remoteConfig

       var settings = remoteConfigSettings {
           minimumFetchIntervalInSeconds = 0

       }

        firebaseRemoteConfig!!.setConfigSettingsAsync(settings)
        firebaseRemoteConfig!!.setDefaultsAsync(R.xml.default_config)

        firebaseRemoteConfig!!.fetchAndActivate().addOnCompleteListener {
            task ->

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
            
            startActivity(Intent(this,LoginActivity::class.java))
            finish()

        }

    }
}