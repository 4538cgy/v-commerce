package com.uos.vcommcerce.activity.videoupload

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.uos.vcommcerce.R
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.databinding.ActivityVideoUpload2Binding
import com.uos.vcommcerce.datamodel.ProductDTO
import com.uos.vcommcerce.util.ProgressDialogLoading
import com.uos.vcommcerce.util.TimeUtil

class VideoUploadActivity : AppCompatActivity() {

    lateinit var binding : ActivityVideoUpload2Binding

    var PICK_IMAGE_FROM_ALBUM = 0
    var videoUri : Uri ? = null

    var firestore = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()
    var auth = FirebaseAuth.getInstance()

    var progressDialog : ProgressDialogLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_upload2)
        binding.activityvideoupload = this@VideoUploadActivity

        binding.activityVideoUploadButtonAddVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {

                type = "video/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
                setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)

            }
            startActivityForResult(intent,PICK_IMAGE_FROM_ALBUM)
        }

        binding.activityVideoUploadButtonUpload.setOnClickListener {
            //으아아 비디오 올리기이잌
            uploadVideo()
        }

        //로딩 초기화
        progressDialog = ProgressDialogLoading(binding.root.context)

        //프로그레스 투명하게
        progressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //프로그레스 꺼짐 방지
        progressDialog!!.setCancelable(false)
    }

    fun uploadProduct(uri : Uri){
        var product = ProductDTO()
        
        println("DB저장 시작")
        
        product.apply {
            videoList?.add(uri.toString())
            sellerUid = auth.currentUser?.uid.toString()
            productCost = binding.activityVideoUploadEidttextProductcost.text.toString()
            productExplain = binding.activityVideoUploadEidttextProductexplain.text.toString()
            productName = binding.activityVideoUploadEidttextProductname.text.toString()
            timestamp = System.currentTimeMillis()
            //현재 모델엔 존재하지 않음
//            timestamp = TimeUtil().getTimeAll()
            sellerAddress = binding.activityVideoUploadEidttextAddress.text.toString()
//            sellerNickName = userModel에서 가져오기
        }
        firestore.collection("product").document("productInfo").collection("normalProduct").document().set(product)
            .addOnSuccessListener {
                progressDialog?.dismiss()
                startActivity(Intent(binding.root.context,SettingActivity::class.java))
                Toast.makeText(binding.root.context,"제품이 데이터베이스에 등록되었습니다 \n 파이어베이스 DB를 확인하세요.",Toast.LENGTH_LONG).show()
            }
    }

    fun uploadVideo(){
        progressDialog?.show()
        println("동영상 업로드 시작")
        //var timestamp = SimpleDateFormat("yy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
        var timestamp = TimeUtil().getTimeAll()
        var imageFileName = "Commerce_Video_" + timestamp + "_.mp4"

        var storageRef = storage?.reference?.child("contents")?.child(imageFileName)

        storageRef?.putFile(videoUri!!)?.continueWithTask { task: com.google.android.gms.tasks.Task<UploadTask.TaskSnapshot> ->

            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->


            println("동영상 업로드 성공")
            uploadProduct(uri)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_FROM_ALBUM)
        {
            if (resultCode == Activity.RESULT_OK){
                //this is path to the selected image
                videoUri = data?.data


            }else{
                //exit the addphoto activity if you leave the album without selecting it
                finish()
            }
        }

        binding.activityVideoTextviewVideoUri.text = videoUri.toString()
    }
}