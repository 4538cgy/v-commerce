package com.uos.vcommcerce.activity.review

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityReviewDetailBinding

class ReviewDetailActivity : BaseActivity<ActivityReviewDetailBinding>(layoutId = R.layout.activity_review_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_detail)
        binding.activityreviewdetail = this@ReviewDetailActivity

        //뒤로가기
        binding.activityReviewDetailImagebuttonClose.setOnClickListener {
            finish()
        }

        //제목 설정
        binding.activityReviewDetailTextviewTitle.text = "으아아?"


        //프로필 이미지 설정
        /*
        * 프로필 이미지 구현 전 까지 임시로 주석
        Glide.with(binding.root.context)
            .load("ImageSrc")
            .into(binding.activityReviewDetailImageviewProfileimage)
         */


    }
}