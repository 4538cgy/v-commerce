package com.uos.vcommcerce.activity.review

import android.content.Context
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
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.ActivityReviewBinding
import com.uos.vcommcerce.databinding.ItemReviewItemBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.datamodel.ReviewDTO
import com.uos.vcommcerce.datamodel.UserVideoData
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import kotlinx.android.synthetic.main.activity_main.*

class ReviewActivity : BaseActivity<ActivityReviewBinding>(layoutId = R.layout.activity_review) {

    var reviewList : ArrayList<ReviewDTO> = arrayListOf(
        ReviewDTO("익명유저","woo9807","reviewid","리뷰 테스트용 내용", arrayListOf<String>(""),5.0,null,null)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityreview = this@ReviewActivity

        //구분선 추가..?
        binding.activityReviewRecycler.addItemDecoration(
            DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL)
        )
        //어댑터 추가
        binding.activityReviewRecycler.adapter = ReviewRecyclerViewAdapter(this,R.layout.item_review_item,reviewList)
        //리사이클러뷰 아래로 설정
        binding.activityReviewRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    fun reviewUpload(view: View) {
        startActivity(Intent(this, ReviewUpload::class.java));
    }

    fun returnReview(view: View) {
        finish()
    }

}


class ReviewRecyclerViewAdapter(var context: Context, layoutId : Int, itemlist : ArrayList<ReviewDTO> )
    : BaseRecyclerAdapter<ReviewDTO, ItemReviewItemBinding>(layoutId,itemlist){

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.adapter = this
    }

    fun itemClick(reviews: ReviewDTO){
        var intent = Intent(context, ReviewDetailActivity::class.java)
        intent.apply {
            putExtra("uid", reviews.id)
            putExtra("nickName", reviews.nickName)
            putExtra("serverTimestamp", reviews.serverTimestamp)
            putExtra("reviewExplain", reviews.reviewExplain)
            putExtra("reviewPoint", reviews.reviewPoint)
            putExtra("reviewId", reviews.reviewId)
            putExtra("reviewImageUrlList", reviews.reviewImageUrlList)
            putExtra("timestamp", reviews.timestamp)
        }
        context.startActivity(intent)

    }
}

