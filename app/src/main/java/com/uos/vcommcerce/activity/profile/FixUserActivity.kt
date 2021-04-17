package com.uos.vcommcerce.activity.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.regist.RegistSellerActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityFixUserBinding
import com.uos.vcommcerce.util.Config
import com.uos.vcommcerce.util.PermissionUtil
import com.uos.vcommcerce.util.Util
import kotlinx.android.synthetic.main.activity_user_view.*

class FixUserActivity : BaseActivity<ActivityFixUserBinding>(layoutId = R.layout.activity_fix_user) {

    var Imguri: MutableLiveData<Uri> = MutableLiveData()
    var NickName: MutableLiveData<String> = MutableLiveData()
    var Introduction: MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fixuseractivity = this

        if (intent.getStringExtra("Name") != null) {
            NickName.value = intent.getStringExtra("Name")
            Log.d("NickName 값: ", NickName.value)
        } else {
            NickName.value = ""
        }

        if (intent.getStringExtra("Introduction") != null) {
            Introduction.value = intent.getStringExtra("Introduction")
            Log.d("Introduction 값: ", Introduction.value)
        } else {
            Introduction.value = ""
        }

        if (intent.getStringExtra("Uri") != "null") {
            Imguri.value = Uri.parse(intent.getStringExtra("Uri"))
            binding.profileImg.setImageURI(Imguri.value)
            Log.d("Uri 값: ", Imguri.toString())

        } else {
            Imguri.value = null
            binding.profileImg.setImageResource(R.mipmap.ic_launcher)
            Log.d("Uri 값: ", "설정안됨")
        }
    }

    //프로필 사진 탭 클릭 이벤트
    fun profileTabClickEvent(view: View) {
        val popup = PopupMenu(applicationContext, view)
        menuInflater.inflate(R.menu.profilepopup, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile_Camera -> {
                    if (PermissionUtil().isPermitted(
                            baseContext,
                            Config.CAMERA_PERMISSION
                        )
                    ) { // 권한 체크하는 함수
                        Util().startCamera(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            Config.CAMERA_PERMISSION,
                            Config.FLAG_PERM_CAMERA
                        )
                    }
                }
                R.id.profile_Gallery -> {
                    if (PermissionUtil().isPermitted(
                            baseContext,
                            Config.STORAGE_PERMISSION
                        )
                    ) { // 권한 체크하는 함수
                        Util().openGallery(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            Config.STORAGE_PERMISSION,
                            Config.FLAG_PERM_STORAGE
                        )
                    }
                }
                R.id.profile_Basic -> {
                    Imguri.value = null
                    binding.profileImg.setImageResource(R.mipmap.ic_launcher)
                }//기본이미지 세팅(현재는 안드로이드..)
            }
            false
        }
        popup.show()
        false
    }

    //카메라 촬영한 이미지 프로필 사진에 넣어주기
    //결과 받아오는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Log.d("카메라","req=$requestCode, result = $resultCode, data = $data")
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Config.FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val filename = Util().newFileName()
                        val uri =
                            Util().saveImageFile(contentResolver, filename, "image/jpg", bitmap)
                        Imguri.value = uri
                        profile_Img.setImageURI(uri)
                    }
                }
                Config.FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    Imguri.value = uri
                    profile_Img.setImageURI(uri)
                }
            }
        }
    }

    //카메라 등 권한 체크
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Config.FLAG_PERM_CAMERA -> {
                var checked = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if (checked) {
                    Util().startCamera(this)
                }
            }
        }
    }

    //결과값 반환
    fun returnResult(view: View) {
        val intent = Intent(binding.root.context, UserActivity::class.java)
        intent.apply {
            putExtra("Name", NickName.value)
            putExtra("Introduction", Introduction.value)
            putExtra("Uri", Imguri.value.toString())
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //결과 반환 취소
    fun CancleResult(view: View) {
        val intent = Intent(binding.root.context, UserActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    //판매자 등록 버튼
    fun RegistSeller(view: View) {
        val intent = Intent(this, RegistSellerActivity::class.java)
        startActivity(intent)
    }

}