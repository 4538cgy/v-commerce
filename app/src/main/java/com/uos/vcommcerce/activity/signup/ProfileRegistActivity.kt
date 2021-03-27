package com.uos.vcommcerce.activity.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityProfileRegistBinding
import com.uos.vcommcerce.popup.PopupManager
import com.uos.vcommcerce.util.Config
import com.uos.vcommcerce.util.PermissionUtil
import com.uos.vcommcerce.util.Util
import kotlinx.android.synthetic.main.activity_user_view.*

class ProfileRegistActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileRegistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_regist)
        binding.activityprofileregist = this@ProfileRegistActivity
        binding.activityProfileRegistImageviewProfilePhoto.setRadius(Util().dpToPx(context = baseContext, dp = 16.0f))
    }

    fun showPicturePopup(view: View){

        PopupManager(this).pictureOptionPopup(object : PopupManager.DialogClickListener{

            //사진 촬영 클릭
            override fun okBtn() {
                if(PermissionUtil().isPermitted(baseContext,Config.CAMERA_PERMISSION )){
                    Util().startCamera(this@ProfileRegistActivity)
                }else{
                    ActivityCompat.requestPermissions(this@ProfileRegistActivity , Config.CAMERA_PERMISSION,
                        Config.FLAG_PERM_CAMERA)
                }
            }
            //사진첩 클릭
            override fun cancelBtn() {
                if(PermissionUtil().isPermitted(baseContext,Config.STORAGE_PERMISSION )){
                    Util().openGallery(this@ProfileRegistActivity)
                }else{
                    ActivityCompat.requestPermissions(this@ProfileRegistActivity , Config.STORAGE_PERMISSION,
                        Config.FLAG_PERM_STORAGE)
                }
            }

        }).show(false)

    }
    fun setBirthDay(view:View){
        PopupManager(this).dataPickerPopup(object : PopupManager.DialogClickCallBackStringListener{
            override fun okBtn(result: String) {
                if(result.isNotEmpty()){
                    binding.birthdaytext = result
                }
            }
        }).show(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Config.FLAG_PERM_CAMERA) {
            if (PermissionUtil().isPermitted(baseContext, Config.CAMERA_PERMISSION)) {
                Util().startCamera(this)
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish()
            }
        }else if(requestCode == Config.FLAG_PERM_STORAGE){
            if(PermissionUtil().isPermitted(baseContext, Config.STORAGE_PERMISSION)){
                Util().openGallery(this)
            }else{
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Config.FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data")!=null){
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val filename = Util().newFileName()
                        val uri = Util().saveImageFile(contentResolver, filename,"image/jpg",bitmap)
                        binding.activityProfileRegistImageviewProfilePhoto.setImageURI(uri)
                    }
                }
                Config.FLAG_REQ_GALLERY ->{
                    val uri = data?.data
                    binding.activityProfileRegistImageviewProfilePhoto.setImageURI(uri)
                }
            }
        }
    }
}