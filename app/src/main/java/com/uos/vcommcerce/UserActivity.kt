package com.uos.vcommcerce

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_user_view.*
import kotlinx.android.synthetic.main.recycler_grid_item.view.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat

private const val FLAG_PERM_CAMERA = 98
private const val FLAG_PERM_STORAGE = 99
private const val FLAG_REQ_CAMERA = 101
private  const val FLAG_REQ_GALLERY = 102

class UserActivity : AppCompatActivity(){
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA) //카메라 퍼미션
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) //외부저장소 권한요청

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)
        //기본 그리드 뷰 실행
        supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, VideoGridFragment()).commit()

        //비디오 버튼 눌럿을 때
        videoGridBtn.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, VideoGridFragment()).commit()

        }
        //히스토리 버튼 눌렀을 때
        historyBtn.setOnClickListener { 
            supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, HistoryFragment()).commit()

        }

        //프로필 사진 이미지 눌렀을 때
        profile_Img.setOnClickListener{ v->
            val popup = PopupMenu(applicationContext, v)
            menuInflater.inflate(R.menu.profilepopup, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile_Camera -> {
                        if(isPermitted(CAMERA_PERMISSION)) { // 권한 체크하는 함수
                            openCamera() 
                        }else{
                            ActivityCompat.requestPermissions(this, CAMERA_PERMISSION,FLAG_PERM_CAMERA )
                        }
                    }
                    R.id.profile_Gallery -> {
                        if(isPermitted(STORAGE_PERMISSION)){ // 권한 체크하는 함수
                            openGallery()
                        }else{
                            ActivityCompat.requestPermissions(this,STORAGE_PERMISSION, FLAG_PERM_STORAGE)
                        }
                    }
                    R.id.profile_Basic -> profile_Img.setImageResource(R.mipmap.ic_launcher_round)  //기본이미지 세팅(현재는 안드로이드..)
                }
                false
            }
            popup.show()
            false
        }
    }

    //갤러리 호출 함수
    fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_GALLERY)
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
        startActivityForResult(intent,FLAG_REQ_CAMERA )
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
        }catch (e:Exception){
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

    //카메라 촬영한 이미지 프로필 사진에 넣어주기
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

                        profile_Img.setImageURI(uri)
                    }
                }
                FLAG_REQ_GALLERY->{
                    val uri = data?.data
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
        when(requestCode){
            FLAG_PERM_CAMERA->{
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

}



