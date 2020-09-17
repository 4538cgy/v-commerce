package com.uos.vcommcerce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.uos.vcommcerce.Util.FcmPush


class SplashActivity : AppCompatActivity() {

    var firebaseRemoteConfig : FirebaseRemoteConfig ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        registerPushToken()

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

    fun registerPushToken(){

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                task ->
            val token = task.result?.token
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val map = mutableMapOf<String,Any>()
            map["pushToken"] = token!!
            FirebaseFirestore.getInstance().collection("pushtokens").document(uid!!).set(map)
        }

    }

    override fun onStop() {
        super.onStop()

        //4538cgy@gmail.com UID 값
        FcmPush.instance.sendMessage("IIBpkwk5jUSNDa0qnDZxgwEvq812","hi","bye")
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