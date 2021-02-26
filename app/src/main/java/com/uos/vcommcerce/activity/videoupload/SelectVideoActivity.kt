package com.uos.vcommcerce.activity.videoupload

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivitySelectVideoBinding
import com.uos.vcommcerce.databinding.SelectVideoItemBinding
import com.uos.vcommcerce.datamodel.TextDTO
import kotlinx.android.synthetic.main.activity_select_video.*


class SelectVideoActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectVideoBinding

    companion object {
        var SelectVideoList: ArrayList<TextDTO> = arrayListOf()
    }


    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375


    //피그마크기1px 당 실제뷰 크기값
    var size_X : Float = 0f
    var size_Y : Float = 0f

    //실제뷰 크기값
    var BottomMin : Int = 0
    var BottomMid : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_video)
        binding.activityselectvideo = this
        SelectVideo.adapter = SelectVideoRecyclerViewAdapter()
        SelectVideo.layoutManager = GridLayoutManager(this,3)

    }


    inner class SelectVideoRecyclerViewAdapter() : RecyclerView.Adapter<SelectVideoRecyclerViewAdapter.CustomViewHolder>() {

        var VideoList: ArrayList<TextDTO> = arrayListOf()

        init {
            VideoList.add(TextDTO("Test1"))
            VideoList.add(TextDTO("Test2"))
            VideoList.add(TextDTO("Test3"))
            VideoList.add(TextDTO("Test4"))
            VideoList.add(TextDTO("Test5"))
            VideoList.add(TextDTO("Test6"))
            VideoList.add(TextDTO("Test7"))
            VideoList.add(TextDTO())

            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var view =LayoutInflater.from(parent.context)
            var binding = SelectVideoItemBinding.inflate(view,parent,false)

            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return VideoList.count()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.bind(VideoList[position])
            //뷰클릭 이벤트 설정
            holder.itemView.setOnClickListener(View.OnClickListener {
                //해당아이템이 선택 안됫을떄
                if(VideoList[position].checked == false){
                    VideoList[position].checked = true
                    SelectVideoList.add(VideoList[position])
                    VideoList[position].liveVisible.value = "VISIBLE"
                    selectedNumCheck()
                    Log.d("추가 확인 "," SelectVideoList : "+ SelectVideoList)
                }else {
                    VideoList[position].checked = false
                    SelectVideoList.remove(VideoList[position])
                    VideoList[position].liveVisible.value = "INVISIBLE"
                    selectedNumCheck()
                    Log.d("제거 확인 ", " SelectVideoList : " + SelectVideoList)
                }
            })
        }

        //번호 교체
        fun selectedNumCheck(){
            for (i in 1..VideoList.count()){
                VideoList[i-1].selectedNum = "";
            }
            for (i in 1..SelectVideoList.count()){
                SelectVideoList[i-1].selectedNum = i.toString();
            }
            if(SelectVideoList.count()==0){
                nextPage.setText("X")
            }else{
                nextPage.setText(">")
            }
            notifyDataSetChanged()
      }

    inner class CustomViewHolder(var binding: SelectVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : TextDTO){
            with(binding){
                value = item
                executePendingBindings()
            }
            binding
        }
    }

    }


    fun Uploadvideo(view: View) {
        val intent = Intent(this, UploadVideoActivity::class.java)
        startActivity(intent)
    }


}