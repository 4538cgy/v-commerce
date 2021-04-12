package com.uos.vcommcerce.activity.videoupload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityUploadVideoBinding
import com.uos.vcommcerce.databinding.UploadVideoItemBinding
import com.uos.vcommcerce.datamodel.TextDTO
import kotlinx.android.synthetic.main.activity_upload_video.*

class UploadVideoActivity : BaseActivity<ActivityUploadVideoBinding>(
    layoutId = R.layout.activity_upload_video
) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityuploadvideo = this
        UploadVideo.adapter = UploadVideoClassRecyclerViewAdapter()
        UploadVideo.layoutManager =
            LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    inner class UploadVideoClassRecyclerViewAdapter() :
        RecyclerView.Adapter<UploadVideoClassRecyclerViewAdapter.CustomViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view = LayoutInflater.from(parent.context)
            var binding = UploadVideoItemBinding.inflate(view, parent, false)

            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return SelectVideoActivity.SelectVideoList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(SelectVideoActivity.SelectVideoList[position])
            //뷰클릭 이벤트 설정
        }

        inner class CustomViewHolder(var binding: UploadVideoItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: TextDTO) {
                with(binding) {
                    value = item
                    executePendingBindings()
                }
                binding
            }
        }

    }

}