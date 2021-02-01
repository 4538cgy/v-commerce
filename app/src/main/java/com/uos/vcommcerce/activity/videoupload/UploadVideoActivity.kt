package com.uos.vcommcerce.activity.videoupload

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityUploadVideoBinding
import com.uos.vcommcerce.databinding.UploadVideoItemBinding
import com.uos.vcommcerce.model.TextDTO
import kotlinx.android.synthetic.main.activity_upload_video.*

class UploadVideoActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadVideoBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_video)
        binding.activityuploadvideo = this
        UploadVideo.adapter = UploadVideoClassRecyclerViewAdapter()
        UploadVideo.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    inner class UploadVideoClassRecyclerViewAdapter() : RecyclerView.Adapter<UploadVideoClassRecyclerViewAdapter.CustomViewHolder>() {




        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = UploadVideoItemBinding.inflate(view,parent,false)

            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return SelectVideoActivity.SelectVideoList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(SelectVideoActivity.SelectVideoList[position])
            //뷰클릭 이벤트 설정
        }

        inner class CustomViewHolder(var binding: UploadVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item : TextDTO){
                with(binding){
                    value = item
                    executePendingBindings()
                }
                binding
            }
        }

    }

}