package com.uos.vcommcerce.activity.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityReviewBinding
import com.uos.vcommcerce.databinding.ItemReviewItemBinding
import com.uos.vcommcerce.datamodel.ReviewDTO

class ReviewActivity : BaseActivity<ActivityReviewBinding>(
    layoutId = R.layout.activity_review
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityreview = this@ReviewActivity

        //구분선 추가..?
        binding.activityReviewRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.root.context,
                DividerItemDecoration.VERTICAL
            )
        )
        //어댑터 추가
        binding.activityReviewRecycler.adapter = ReviewRecyclerViewAdapter()
        //리사이클러뷰 아래로 설정
        binding.activityReviewRecycler.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
    }

    fun reviewUpload(view: View) {
        startActivity(Intent(this, ReviewUpload::class.java));
    }


    fun returnReview(view: View) {
        finish()
    }


    inner class ReviewRecyclerViewAdapter() :
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewRecyclerViewAdapterViewHolder>() {

        var reviews: ArrayList<ReviewDTO> = arrayListOf()
        var data = listOf<ReviewDTO>()

        init {
            notifyDataSetChanged()
            data = reviews
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ReviewRecyclerViewAdapter.ReviewRecyclerViewAdapterViewHolder {

            val binding = ItemReviewItemBinding.inflate(
                LayoutInflater.from(binding.root.context),
                parent,
                false
            )
            return ReviewRecyclerViewAdapterViewHolder(binding)
        }


        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ReviewRecyclerViewAdapterViewHolder, position: Int) {
            holder.onBind(data[position])


            //아이템 자체 클릭
            holder.itemView.setOnClickListener {
                var intent = Intent(binding.root.context, ReviewDetailActivity::class.java)
                intent.apply {

                    putExtra("uid", reviews[position].id)
                    putExtra("nickName", reviews[position].nickName)
                    putExtra("serverTimestamp", reviews[position].serverTimestamp)
                    putExtra("reviewExplain", reviews[position].reviewExplain)
                    putExtra("reviewPoint", reviews[position].reviewPoint)
                    putExtra("reviewId", reviews[position].reviewId)
                    putExtra("reviewImageUrlList", reviews[position].reviewImageUrlList)
                    putExtra("timestamp", reviews[position].timestamp)

                }

                holder.binding.root.context.startActivity(intent)
            }

            //사진 추가
            if (reviews[position].reviewImageUrlList?.isEmpty() == false) {
                Glide.with(holder.itemView.context)
                    .load(reviews[position].reviewImageUrlList?.get(0))
                    .into(holder.binding.itemReviewItemIamgeviewPhoto)
            }

            //프로필 이미지 추가


        }

        inner class ReviewRecyclerViewAdapterViewHolder(val binding: ItemReviewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: ReviewDTO) {
                binding.itemreviewitem = data
            }
        }


    }


}