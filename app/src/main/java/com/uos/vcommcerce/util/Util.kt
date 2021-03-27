package com.uos.vcommcerce.util

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class Util {
    // 이메일 형태 체크
    fun checkEmail(email: String): Boolean {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
    }
    // 비밀번호 체크 숫자 영문(대소문자) 특수문자 8자 이상
    fun checkPassword(password: String, passwordRe: String): Boolean {
        if (password.isEmpty() || passwordRe.isEmpty()) {
            return false
        }
        if (password != passwordRe) {
            return false
        }
        if(password.length < 8){
            return  false
        }
        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~@$!%*#?&])[a-z[A-Z][0-9]$@!%*#?&]{8,}$"
        return Pattern.matches(pattern,password)
    }

    fun newFileName() : String{
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return filename;
    }

    //이미지 저장
    fun saveImageFile(contentResolver: ContentResolver,filename:String, mimeType:String, bitmap: Bitmap) : Uri? {
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

    fun dpToPx(context : Context, dp : Float) : Float {
        val dm = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp , dm)

    }


    fun startCamera(activity : Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, Config.FLAG_REQ_CAMERA)
    }
    fun openGallery(activity : Activity){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        activity.startActivityForResult(intent, Config.FLAG_REQ_GALLERY)
    }

    fun cropImage(uri : Uri, activity : Activity){
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            //사각형 모양으로 자른다
            .start(activity)
    }
}