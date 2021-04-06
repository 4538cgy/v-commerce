package com.uos.vcommcerce.activity.review

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityReviewUploadBinding
import com.uos.vcommcerce.databinding.ActivityReviewUploadItemBinding
import com.uos.vcommcerce.databinding.ActivityReviewUploadItemFooterBinding
import com.uos.vcommcerce.datamodel.review.ReviewUploadItemDTO
import com.uos.vcommcerce.datamodel.review.ReviewUploadItemFooterDTO

class ReviewUpload : AppCompatActivity() {

    val FLAG_REQ_GALLERY = 102

    lateinit var binding: ActivityReviewUploadBinding

    lateinit var adapter : reviewUploadPictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_upload);
        binding.reviewupload = this

        adapter = reviewUploadPictureAdapter()
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




    inner class reviewUploadPictureAdapter() : RecyclerView.Adapter<reviewUploadPictureAdapter.CustomViewHolder>() {

        private val TYPE_ITEM = 0
        private val TYPE_FOOTER = 1

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
            fun onBind(data: ReviewUploadItemFooterDTO) {
                binding.reviewuploadItemFooter = data
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
                    holder.onBind(ReviewUploadItemFooterDTO(this@ReviewUpload))
                }
            }
        }
    }
}