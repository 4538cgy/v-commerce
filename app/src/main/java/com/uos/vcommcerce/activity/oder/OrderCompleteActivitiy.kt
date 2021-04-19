package com.uos.vcommcerce.activity.oder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.SettingActivity
import com.uos.vcommcerce.activity.profile.UserActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.ActivityOderCompleteActivitiyBinding
import com.uos.vcommcerce.databinding.ItemRecommendedProductBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_view.*

data class RecommendedProductData(
    var productImg: String,
    var productName: String,
    var productPrice: String
)

class OrderCompleteActivitiy : BaseActivity<ActivityOderCompleteActivitiyBinding>(layoutId = R.layout.activity_oder_complete_activitiy) {
    val sampleData = arrayListOf<RecommendedProductData>(
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScAV7G5jua33e66J2vVpL88cX7Onn9kdcqyw&usqp=CAU",
            "1번",
            "10000원"
        ),
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2y7EVaFYQ2uJ4Zl9y12-nV7X4l69yWPGTjA&usqp=CAU",
            "3번",
            "120000원"
        ),
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTgLnniLA0dWCgbvbS5EIYL-ANWzsQybuGLg&usqp=CAU",
            "44번",
            "510000원"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityordercompleteactivity = this@OrderCompleteActivitiy

        //binding.recommendedProductRecyclerview.layoutManager = GridLayoutManager(this,1)
        binding.recommendedProductRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.recommendedProductRecyclerview.adapter = RecommendedProductAdapter(this,R.layout.item_recommended_product,sampleData)

        //뒤로가기
        binding.activityOrderCompleteActivityImagebuttonClose.setOnClickListener {
            finish()
        }

        binding.activityOrderCompleteActivityButtonGoMain.setOnClickListener {
            startActivity(Intent(binding.root.context, SettingActivity::class.java))
            finish()
        }

        binding.activityOrderCompleteActivityButtonGoHistory.setOnClickListener {
            val intent = Intent(binding.root.context, UserActivity::class.java)
            intent.putExtra("history", "history")
            startActivity(intent)
            finish()
        }
    }
}

//Toast 때문에 생성때 context 가져옴 해당 부분 사라지면 context넘겨줄 필요 없어짐
class RecommendedProductAdapter(val context: Context,layoutId: Int,itemlist : ArrayList<RecommendedProductData>)
    : BaseRecyclerAdapter<RecommendedProductData,ItemRecommendedProductBinding>(layoutId,itemlist){

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        super.onBindViewHolder(holder,position)
        //그리드 버튼 클릭시

    }

    fun itemClick(view: View, recommendedProductData: RecommendedProductData){
        Toast.makeText(context, recommendedProductData.productName, Toast.LENGTH_SHORT).show()

//        val vedioIntent = Intent( context, TestExoplayerActivity::class.java)
//        vedioIntent.putExtra("title", recommendedProductData.gridTitle)
//        vedioIntent.putExtra("img", recommendedProductData.productImg)
//        vedioIntent.putExtra("url", recommendedProductData.pro)
//        context.startActivity(vedioIntent)
    }
}