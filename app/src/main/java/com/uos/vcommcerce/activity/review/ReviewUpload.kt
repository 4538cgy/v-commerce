package com.uos.vcommcerce.activity.review

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.*
import com.uos.vcommcerce.datamodel.ReviewDTO
import com.uos.vcommcerce.datamodel.ReviewUploadItemDTO

import kotlinx.android.synthetic.main.activity_user_view.*

class ReviewUpload : AppCompatActivity() {

    val FLAG_REQ_GALLERY = 102

    lateinit var binding: ActivityReviewUploadBinding

    lateinit var adapter : ReviewUploadPictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_upload);
        binding.reviewupload = this

        adapter = ReviewUploadPictureAdapter(this)
        binding.reviewImgRecycler.adapter = adapter
        binding.reviewImgRecycler.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)
        adapter.notifyDataSetChanged();

    }

    //사진 받아온것 처리하는 코드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Log.d("카메라","req=$requestCode, result = $resultCode, data = $data")
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_GALLERY ->{
                    adapter.addItem(ReviewUploadItemDTO(data?.data))
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}



class ReviewUploadPictureAdapter(var activity: Activity) : RecyclerView.Adapter<ReviewUploadPictureAdapter.CustomViewHolder>() {

    private val TYPE_ITEM = 0
    private val TYPE_FOOTER = 1

    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) //외부저장소 권한요청
    val FLAG_REQ_GALLERY = 102
    val FLAG_PERM_STORAGE = 99


    var reviewList : ArrayList<ReviewUploadItemDTO> = arrayListOf();

    //베이스가 될 커스텀 홀더
    open inner class CustomViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun onBind(){}
    }

    //아이템 홀더
    inner class ReviewUploadItem(val binding: ActivityReviewUploadItemBinding) : CustomViewHolder(binding.root) {
        fun onBind(data: ReviewUploadItemDTO) {
            binding.reviewuploadItem = data
        }
    }

    //푸터 홀더
    inner class ReviewUploadItemFooter(val binding: ActivityReviewUploadItemFooterBinding) : CustomViewHolder(binding.root) {
        fun onBind(adapter: ReviewUploadPictureAdapter) {
            binding.adapter = adapter
        }
    }

    fun addItem(ReviewUploadItem : ReviewUploadItemDTO){
        reviewList.add(ReviewUploadItem);
    }


    //여기서 바인딩을 할당
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        if(viewType == TYPE_FOOTER){
            val inflaterView = LayoutInflater.from(parent.context)
            val binding = ActivityReviewUploadItemFooterBinding.inflate(inflaterView,parent,false);
            return ReviewUploadItemFooter(binding)
        }else{
            val inflaterView = LayoutInflater.from(parent.context)
            val binding = ActivityReviewUploadItemBinding.inflate(inflaterView,parent,false);
            return ReviewUploadItem(binding)
        }
    }

    //홀더의 뷰타입을 커스텀으로 설정
    override fun getItemViewType(position: Int): Int {
        return if(position == reviewList.count()){
            TYPE_FOOTER
        }else{
            TYPE_ITEM

        }
    }

    //footer를 포함하여 +1개 를 반환
    override fun getItemCount(): Int {
        return reviewList.count()+1
    }

    //뷰홀더에 데이터 할당
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_ITEM ->{
                var holder = holder as ReviewUploadItem
                holder.onBind(reviewList[position])
            }
            TYPE_FOOTER ->{
                var holder = holder as ReviewUploadItemFooter
                holder.onBind(this)
            }
        }
    }



    fun addPicture(view: View) {
        if (isPermitted(STORAGE_PERMISSION)) { // 권한 체크하는 함수
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            ActivityCompat.startActivityForResult(activity, intent, FLAG_REQ_GALLERY, Bundle())
        } else {
            ActivityCompat.requestPermissions(activity, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
        }

    }
    fun isPermitted(permissions:Array<String>) : Boolean{
        for (permission in permissions){
            val result = ContextCompat.checkSelfPermission(activity , permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
}