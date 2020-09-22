package com.uos.vcommcerce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_grid_view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val MY_PERMISSION_CAMERA = 1111
private const val REQUEST_TAKE_PHOTO = 2222
private const val REQUEST_TAKE_ALBUM = 3333
private const val REQUEST_IMAGE_CROP = 4444

public const val REQUESTCODE = 101


class GridActivity : AppCompatActivity(){
    lateinit var mCurrentPhotoPath:String
    lateinit var imageURI:Uri
    lateinit var photoURI:Uri
    lateinit var albumURI:Uri

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
        setContentView(R.layout.activity_recycler_grid_view)


        //recyclerGridView.layoutManager = LinearLayoutManager(this)
        recyclerGridView.layoutManager = GridLayoutManager(this, 3)
        recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, this)

        profile_Img.setOnClickListener{v->
                val popup = PopupMenu(applicationContext, v)
                menuInflater.inflate(R.menu.menu, popup.menu)

                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.one -> CaptureCamera()
                        R.id.two -> GetAlbum()
                        R.id.three -> profile_Img.setImageResource(R.mipmap.ic_launcher_round)  //기본이미지 세팅(현재는 안드로이드..)
                    }
                    false
                }
                popup.show()
                //checkPermission()
                false
            }


//        profile_Img.setOnClickListener {

//                v->
//            //카메라에 대한 권한을 받아오는 코드 (테스트용)
//            val permission: Int =
//                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//            if (permission == PackageManager.PERMISSION_DENIED) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.CAMERA),
//                    0
//                )
//            } else {
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, 1)
//            }
            //Toast.makeText(v.context, "클릭", Toast.LENGTH_SHORT).show()
//        }
    }

    //촬영함수
    fun CaptureCamera(){
        var state:String = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED.equals(state)){
            var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(takePictureIntent.resolveActivity(packageManager) != null){
                var photoFile: File? = null
                try{
                    photoFile = CreateImageFile()
                }catch (e:IOException){
                    e.printStackTrace()
                }

                if(photoFile != null){
                    var providerUri:Uri = FileProvider.getUriForFile(this,packageName,photoFile)
                    imageURI = providerUri

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }else{
                Toast.makeText(this,"접근 불가능 합니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //촬영 혹은 크롭된 사진에 대한 새로운 이미지 저장 함수
    fun CreateImageFile():File{
        var timeStamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "JPEG_"+timeStamp + ",jpg"
        var imageFile:File?
        var storageDir = File(Environment.getExternalStorageDirectory() , "/Pictures")

        if(!storageDir.exists()){
            storageDir.mkdirs()
        }

        imageFile = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath as Nothing

        return imageFile
    }

    //갤러리 기능
    fun GetAlbum(){
        var intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, REQUEST_TAKE_ALBUM)
    }

    //사진 크롭할 수 있도록 하는 함수
    fun CropImage(){
        var cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        cropIntent.setDataAndType(photoURI,"image/*")
        cropIntent.putExtra("aspectX",1)
        cropIntent.putExtra("aspectY",1)
        cropIntent.putExtra("scale",true)
        cropIntent.putExtra("output", albumURI)
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP)
    }

    //갤러리에 사진 추가 함수
    fun GalleryAddPic(){
        var mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        var file:File = File(mCurrentPhotoPath)
        var contentURI = Uri.fromFile(file)
        mediaScanIntent.setData(contentURI)
        sendBroadcast(mediaScanIntent)
        Toast.makeText(this,"앨범에 저장되었습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if(id==1){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "카메라 권한 승인 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "카메라 권한 승인 거절", Toast.LENGTH_SHORT).show()
            }
        }
    }
}