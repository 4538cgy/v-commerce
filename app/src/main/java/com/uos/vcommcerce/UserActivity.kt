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

    val DataList = arrayListOf(
        GridData(R.drawable.ic_launcher_background, "0번"),
        GridData(R.drawable.ic_launcher_background, "1번"),
        GridData(R.drawable.btn_signin_facebook, "2번"),
        GridData(R.drawable.ic_launcher_background, "3번"),
        GridData(R.drawable.ic_launcher_background, "4번"),
        GridData(R.drawable.ic_launcher_background, "5번"),
        GridData(R.drawable.com_facebook_auth_dialog_cancel_background, "6번"),
        GridData(R.drawable.ic_launcher_background, "7번"),
        GridData(R.drawable.ic_launcher_background, "8번"),
        GridData(R.drawable.com_facebook_button_icon_white, "9번"),
        GridData(R.drawable.ic_launcher_background, "10번"),
        GridData(R.drawable.com_facebook_tooltip_black_xout, "11번"),
        GridData(R.drawable.common_google_signin_btn_text_disabled, "12번"),
        GridData(R.drawable.ic_launcher_background, "13번"),
        GridData(R.drawable.ic_launcher_background, "14번"),
        GridData(R.drawable.messenger_bubble_large_white, "15번"),
        GridData(R.drawable.ic_launcher_background, "16번"),
        GridData(R.drawable.messenger_button_white_bg_selector, "17번"),
        GridData(R.drawable.ic_launcher_background, "18번"),
        GridData(R.drawable.ic_launcher_background, "19번"),
        GridData(R.drawable.ic_launcher_background, "20번"),
        GridData(R.drawable.ic_launcher_background, "21번"),
        GridData(R.drawable.ic_launcher_background, "22번"),
        GridData(R.drawable.ic_launcher_background, "23번"),
        GridData(R.drawable.ic_launcher_background, "24번")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        recyclerGridView.layoutManager = GridLayoutManager(this, 3)
        recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, this)

        profile_Img.setOnClickListener{ v->
            val popup = PopupMenu(applicationContext, v)
            menuInflater.inflate(R.menu.profilepopup, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile_Camera -> {
                        if(isPermitted(CAMERA_PERMISSION)) {// 권한 체크하는 함수
                            openCamera()
                        }else{
                            ActivityCompat.requestPermissions(this, CAMERA_PERMISSION,FLAG_PERM_CAMERA )
                        }
                    }
                    R.id.profile_Gallery -> {
                        if(isPermitted(STORAGE_PERMISSION)){
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


    //그리드 데이터
    inner class GridData(val img:Int, val title:String)


    inner class RecyclerGridViewHolder(v : View) : RecyclerView.ViewHolder(v){

        val img = v.content_Img
        val title = v.content_Text
    }

    inner class RecyclerGridViewAdapter(val DataList:ArrayList<GridData>, val context: Context) : RecyclerView.Adapter<RecyclerGridViewHolder>() {
        //생성하는부분
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
            val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false)
            return RecyclerGridViewHolder(cellForRow);
        }
        //보여줄 개수
        override fun getItemCount() = DataList.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
            val data = DataList[position]
            holder.img.setImageResource(DataList[position].img)
            holder.title.text = DataList[position].title

            //그리드 안 이미지(item) 클릭시 나중에 영상 재생?
            holder.itemView.setOnClickListener{
                Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
            }

        }
    }
}



