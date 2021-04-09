package com.uos.vcommcerce.activity.oder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.activity.profile.UserActivity
import com.uos.vcommcerce.databinding.ActivityOderCompleteActivitiyBinding
import com.uos.vcommcerce.databinding.ItemRecommendedProductBinding
import kotlinx.android.synthetic.main.activity_user_view.*

data class RecommendedProductData(
    var productImg: String,
    var productName: String,
    var productPrice: String
)
class OrderCompleteActivitiy : AppCompatActivity() {
    val sampleData = listOf(
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScAV7G5jua33e66J2vVpL88cX7Onn9kdcqyw&usqp=CAU",
            "1번",
            "10000원"),
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2y7EVaFYQ2uJ4Zl9y12-nV7X4l69yWPGTjA&usqp=CAU",
            "3번",
            "120000원"),
        RecommendedProductData(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTgLnniLA0dWCgbvbS5EIYL-ANWzsQybuGLg&usqp=CAU",
            "44번",
            "510000원")

        )


    lateinit var binding : ActivityOderCompleteActivitiyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_oder_complete_activitiy)
        binding.activityordercompleteactivity = this@OrderCompleteActivitiy

        val recommendedProductAdapter = RecommendedProductAdapter(this)
        //binding.recommendedProductRecyclerview.layoutManager = GridLayoutManager(this,1)
        binding.recommendedProductRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.recommendedProductRecyclerview.adapter = recommendedProductAdapter
        recommendedProductAdapter.data = sampleData
        
        //뒤로가기
        binding.activityOrderCompleteActivityImagebuttonClose.setOnClickListener { 
            finish()
        }


        binding.activityOrderCompleteActivityButtonGoMain.setOnClickListener {
            startActivity(Intent(binding.root.context , SettingActivity::class.java))
            finish()
        }

        binding.activityOrderCompleteActivityButtonGoHistory.setOnClickListener {
            val intent = Intent(binding.root.context, UserActivity::class.java)
            intent.putExtra("history", "history")
            startActivity(intent)
            finish()
        }
    }


    inner class RecommendedProductViewHolder(val binding : ItemRecommendedProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: RecommendedProductData){
            binding.recommendedproduct = data
        }
    }

    inner class RecommendedProductAdapter(val context: Context): RecyclerView.Adapter<RecommendedProductViewHolder>(){

        var data = listOf<RecommendedProductData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedProductViewHolder {
            val binding = ItemRecommendedProductBinding.inflate(
                LayoutInflater.from(context), parent, false)

            return RecommendedProductViewHolder(binding)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: RecommendedProductViewHolder, position: Int) {
            holder.onBind(data[position])

            //그리드 버튼 클릭시
            holder.itemView.setOnClickListener{
                Toast.makeText(context, data[position].productImg, Toast.LENGTH_SHORT).show()

//                val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
//                vedioIntent.putExtra("title", data[position].gridTitle)
//                vedioIntent.putExtra("img", data[position].gridImg)
//                vedioIntent.putExtra("url", data[position].gridUrl)
//                startActivity(vedioIntent)

            }
        }
    }
}