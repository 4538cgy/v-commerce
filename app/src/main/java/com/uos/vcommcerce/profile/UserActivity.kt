package com.uos.vcommcerce.profile

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.review.ReviewDetailActivity
import com.uos.vcommcerce.databinding.ActivityUserViewBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_view.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat

private const val FLAG_PERM_CAMERA = 98
private const val FLAG_PERM_STORAGE = 99
private const val FLAG_REQ_CAMERA = 101
private const val FLAG_REQ_GALLERY = 102
private const val FLAG_FIX_RESULT = 0

class UserActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserViewBinding
    private var firebaseAuth : FirebaseAuth? = null;


    var Imguri : Uri? = null
    var NickName : String? = "Nickname"
    var Introduction : String? = "나는 낭만고양이\nSweet little kitty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_view)
        binding.useractivity = this@UserActivity
        firebaseAuth = FirebaseAuth.getInstance()
        //setContentView(R.layout.activity_user_view)

        //기본 그리드 뷰 실행
        if(firebaseAuth?.currentUser != null){   //로그인 체크 이거 맞나?
            binding.followBtn.visibility = View.VISIBLE
            binding.messageBtn.visibility = View.VISIBLE
        }else{
            binding.followBtn.visibility = View.INVISIBLE
            binding.messageBtn.visibility = View.INVISIBLE
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.recyclerViewBox, 
            VideoGridFragment()
        ).commit()

        if(intent.getStringExtra("history") == "history"){

            supportFragmentManager.beginTransaction().replace(
                R.id.recyclerViewBox,
                HistoryFragment()
            ).commit()
        }

    }

    //비디오 탭 클릭 이벤트
    fun videoTabClickEvent(view: View){
        supportFragmentManager.beginTransaction().replace(
            R.id.recyclerViewBox,
            VideoGridFragment()
        ).commit()
    }

    //히스토리 탭 클릭 이벤트
    fun historyTabClickEvent(view: View){
        supportFragmentManager.beginTransaction().replace(
            R.id.recyclerViewBox,
            HistoryFragment()
        ).commit()
    }

    fun moveFixUserActivity(view:View){
        var intent = Intent(binding.root.context, FixUserActivity::class.java)
        intent.apply {
            putExtra("Name",NickName)
            putExtra("Introduction",Introduction)
            putExtra("Uri",Imguri.toString())
        }
        startActivityForResult(intent, FLAG_FIX_RESULT)
    }

}



