package com.uos.vcommcerce.activity.splash

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.login.LoginActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivitySplashBinding
import java.security.MessageDigest


class SplashActivity : BaseActivity<ActivitySplashBinding>(
    layoutId = R.layout.activity_splash
) {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitysplash = this@SplashActivity

        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        firebaseRemoteConfig.fetch(0).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.fetchAndActivate()
                DialogDisplay(firebaseRemoteConfig)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("앱 실행에 오류가 발생하였습니다. 다시 실행해주시기 바랍니다.")
                    .setCancelable(false)
                    .setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which ->
                        this.finishAffinity()
                    }).show()
            }
        }


        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = info.signingInfo.apkContentsSigners
            val md = MessageDigest.getInstance("SHA")
            for (signature in signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val key = String(Base64.encode(md.digest(), 0))
                Log.d("HASH KEY : ", "---- $key ----")

            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }

    }

    //앱버전 체크
    fun appVersionCheckWithRemoteConfig(): String {

        val packageManager = this.packageManager

        return packageManager.getPackageInfo(
            this.packageName,
            PackageManager.GET_ACTIVITIES
        ).versionName
    }


    fun DialogDisplay(firebaseRemoteConfig: FirebaseRemoteConfig) {
        val strVersionName = appVersionCheckWithRemoteConfig()
        //version from Firebase
        var strLatestVersion = firebaseRemoteConfig.getString("message_version")
        var strMaintenanceCheck = firebaseRemoteConfig.getBoolean("check_maintenance")

        if (strMaintenanceCheck) {
            DialogDisplayDownServer()
        } else {
            if (strVersionName != strLatestVersion) {
                AlertDialog.Builder(this)
                    .setTitle("Update")
                    .setMessage("최신 버전의 앱을 설치 후 재실행 해주시기 바랍니다.")
                    .setCancelable(false)
                    .setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which ->
                        this.finish()
                    }).show()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }
        }


    }

    fun DialogDisplayDownServer() {
        AlertDialog.Builder(this)
            .setTitle("Maintenance")
            .setMessage("서버 점검중입니다. \n PM 06:00 ~ PM 08:00")
            .setCancelable(false)
            .setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which ->
                this.finishAffinity()
            }).show()
    }


}