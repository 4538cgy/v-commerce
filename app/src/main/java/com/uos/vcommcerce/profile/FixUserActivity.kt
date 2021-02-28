package com.uos.vcommcerce.profile

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.regist.RegistSellerActivity
import com.uos.vcommcerce.databinding.ActivityFixUserBinding
import kotlinx.android.synthetic.main.activity_user_view.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat

private const val FLAG_PERM_CAMERA = 98
private const val FLAG_PERM_STORAGE = 99
private const val FLAG_REQ_CAMERA = 101
private const val FLAG_REQ_GALLERY = 102
private const val FLAG_FIX_RESULT = 103

class FixUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFixUserBinding

    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA) //카메라 퍼미션
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) //외부저장소 권한요청

    var Imguri :MutableLiveData<Uri> = MutableLiveData()
    var NickName : MutableLiveData<String> = MutableLiveData()
    var Introduction : MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_user)
        binding.fixuseractivity = this



        if(intent.getStringExtra("Name") != null){
            NickName.value = intent.getStringExtra("Name")
            Log.d("NickName 값: " ,NickName.value)
        }else{
            NickName.value = ""
        }

        if(intent.getStringExtra("Introduction") != null){
            Introduction.value = intent.getStringExtra("Introduction")
            Log.d("Introduction 값: " ,Introduction.value)
        }else{
            Introduction.value = ""
        }

        if(intent.getStringExtra("Uri") != "null"){
            Imguri.value = Uri.parse(intent.getStringExtra("Uri"))
            binding.profileImg.setImageURI(Imguri.value)
            Log.d("Uri 값: " ,Imguri.toString())

        }else{
            Imguri.value = null
            binding.profileImg.setImageResource(R.mipmap.ic_launcher)
            Log.d("Uri 값: " ,"설정안됨")
        }
    }



    //프로필 사진 탭 클릭 이벤트
    fun profileTabClickEvent(view: View){
        val popup = PopupMenu(applicationContext, view)
        menuInflater.inflate(R.menu.profilepopup, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile_Camera -> {
                    if(isPermitted(CAMERA_PERMISSION)) { // 권한 체크하는 함수
                        openCamera()
                    }else{
                        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, FLAG_PERM_CAMERA)
                    }
                }
                R.id.profile_Gallery -> {
                    if(isPermitted(STORAGE_PERMISSION)){ // 권한 체크하는 함수
                        openGallery()
                    }else{
                        ActivityCompat.requestPermissions(this,STORAGE_PERMISSION, FLAG_PERM_STORAGE)
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
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data")!=null){
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val filename = newFileName()
                        val uri = saveImageFile(filename,"image/jpg",bitmap)
                        Imguri.value = uri
                        profile_Img.setImageURI(uri)
                    }
                }
                FLAG_REQ_GALLERY ->{
                    val uri = data?.data
                    Imguri.value = uri
                    profile_Img.setImageURI(uri)
                }
            }
        }
    }



    //카메라 권한 여부 체크
    fun isPermitted(permissions:Array<String>) : Boolean{

        for (permission in permissions){
            val result = ContextCompat.checkSelfPermission(this, permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    //카메라 열기
    fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }

    //카메라 등 권한 체크
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            FLAG_PERM_CAMERA ->{
                var checked = true
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        checked = false
                        break
                    }
                }
                if(checked){
                    openCamera()
                }
            }
        }
    }

    //이미지 저장
    fun saveImageFile(filename:String, mimeType:String, bitmap: Bitmap) : Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        //안드로이드 버전이 Q보다 크거나 같으면
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING, 1)   //사용중임을 알려주는 코드
        }

        //내가 저장할 사진의 주소값 생성
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try{
            if(uri != null){
                //쓰기모드 열기
                var descriptor = contentResolver.openFileDescriptor(uri,"w")
                if(descriptor != null){
                    val fos = FileOutputStream(descriptor.fileDescriptor)   //OutputStream 예외처리
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                    return uri
                }
            }
        }catch (e: Exception){
            Log.e("Camera","${e.localizedMessage}")
        }

        return null
    }

    //파일명 생성 함수
    fun newFileName() : String{
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return filename;
    }



    //갤러리 호출 함수
    fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_GALLERY)
    }

    //결과값 반환
    fun returnResult(view: View){
        var intent = Intent(binding.root.context, UserActivity::class.java)
        intent.apply {
            putExtra("Name",NickName.value)
            putExtra("Introduction",Introduction.value)
            putExtra("Uri",Imguri.value.toString())
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //결과 반환 취소
    fun CancleResult(view: View){
        var intent = Intent(binding.root.context, UserActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    //판매자 등록 버튼
    fun RegistSeller(view :View){
        val intent = Intent(this, RegistSellerActivity::class.java)
        startActivity(intent)
    }

}