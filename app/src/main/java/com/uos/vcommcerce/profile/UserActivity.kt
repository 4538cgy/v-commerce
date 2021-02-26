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
import android.os.Debug
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.productinformation.ProductInformationActivity
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
private const val FLAG_FIX_RESULT = 103

class UserActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserViewBinding
    private var firebaseAuth : FirebaseAuth? = null;



    var Imguri : ObservableField<Uri?> = ObservableField()
    var NickName : ObservableField<String> = ObservableField("Nickname")
    var Introduction : ObservableField<String> = ObservableField("나는 낭만고양이\nSweet little kitty")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_view)
        binding.useractivity = this
        firebaseAuth = FirebaseAuth.getInstance()

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

    //프로필 수정 페이지로 이동
    fun moveFixUserActivity(view:View){
        var intent = Intent(binding.root.context, FixUserActivity::class.java)
        intent.apply {
            putExtra("Name",NickName.get())
            putExtra("Introduction",Introduction.get())
            putExtra("Uri",Imguri.get().toString())
        }
        startActivityForResult(intent, FLAG_FIX_RESULT)
    }

    //결과 받아오는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Log.d("카메라","req=$requestCode, result = $resultCode, data = $data")
        if(resultCode == Activity.RESULT_OK){
            Log.d("resultCode : ","Activity.RESULT_OK")

            if(data?.getStringExtra("Name") != null){
                NickName.set(data?.getStringExtra("Name"))
                Log.d("NickName 값: " ,NickName.get())
            }else{
                NickName.set("")
            }

            if(data?.getStringExtra("Introduction") != null){
                Introduction.set(data?.getStringExtra("Introduction"))
                Log.d("Introduction 값: " ,Introduction.get())
            }else{
                Introduction.set("")
            }

            Log.d("Uri 값: " ,data?.getStringExtra("Uri"))

            if(data?.getStringExtra("Uri") != "null"){
                Imguri.set(Uri.parse(data?.getStringExtra("Uri")))
                binding.profileImg.setImageURI(Imguri.get())
                Log.d("Uri 값: " ,Imguri.get().toString())

            }else{
                Imguri.set(null)
                binding.profileImg.setImageResource(R.mipmap.ic_launcher)
                Log.d("Uri 값: " ,"설정안됨")
            }

        }else if(resultCode == Activity.RESULT_CANCELED){
            Log.d("resultCode : ","Activity.RESULT_CANCELED")
        }
    }

    fun moveProductInfomation(view: View){
        var intent = Intent(binding.root.context, ProductInformationActivity::class.java)
        startActivity(intent)
    }

}



