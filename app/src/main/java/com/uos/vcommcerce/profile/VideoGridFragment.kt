package com.uos.vcommcerce.profile

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.*
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uos.vcommcerce.Model.UserDataVM
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.FragmentVideoGridBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.model.UserVideoData
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import com.uos.vcommcerce.util.setHeight


class VideoGridFragment : Fragment() {

    lateinit var binding:FragmentVideoGridBinding
    //나중에 값이 설정될거라고 lateinit으로 설정
    lateinit var userDataViewModel: UserDataVM
    //lateinit var recyclerGridView :RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //val fragmentView = inflater.inflate(R.layout.fragment_video_grid, container, false)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_video_grid, container,false)
        binding.recyclerGridView.layoutManager = GridLayoutManager(requireContext(),3)

        //binding.recyclerGridView.adapter = RecyclerGridViewAdapter(dataList, requireContext())

        userDataViewModel = ViewModelProvider(this).get(UserDataVM::class.java)
        userDataViewModel.gridData.observe(this, Observer {
            binding.recyclerGridView.adapter = RecyclerGridViewAdapter(it, requireContext())
        })

        //binding.recyclerGridView.adapter = RecyclerGridViewAdapter(dataList, requireContext())
       // binding.recyclerGridView.adapter = RecyclerGridViewAdapter()

        val fragmentView = binding.root
        //리사이클러뷰
        //recyclerGridView = fragmentView.findViewById(R.id.recyclerGridView)
        //recyclerGridView.layoutManager = GridLayoutManager(requireContext(), 3)
        //recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, requireContext())
        return fragmentView
    }

    //그리드 데이터
    //inner class GridData(val img:Int, val title:String, val videoUrl:String)


    inner class RecyclerGridViewHolder(val binding: VideoGridItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : UserVideoData){
            binding.griditem = data
            binding.executePendingBindings()
        }

    }

    inner class RecyclerGridViewAdapter(var data:List<UserVideoData> ,val context: Context)  : RecyclerView.Adapter<RecyclerGridViewHolder>() {
        //생성하는부분
        fun setDataList(gridData: List<UserVideoData>) {
            this.data = gridData
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
            val binding = VideoGridItemBinding.inflate(LayoutInflater.from(context),parent,false)
            //val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false)
            return RecyclerGridViewHolder(binding)
        }
        //보여줄 개수
        override fun getItemCount() = data.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
            holder.onBind(data[position])

            //그리드 안 이미지(item) 클릭시 나중에 영상 재생?
            holder.itemView.setOnClickListener{
                Toast.makeText(context,"영상 클릭됨" , Toast.LENGTH_SHORT).show()

                val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
                vedioIntent.putExtra("img", data[position].Img)
                vedioIntent.putExtra("url", data[position].Url)
                startActivity(vedioIntent)
            }

            //페이지 높이 증가
            var lowcount : Int = getItemCount()/3+1
            binding.recyclerGridView.setHeight(222*lowcount)
        }
    }

//    inner class RecyclerGridViewAdapter(var data:List<GridData>, val context: Context) : RecyclerView.Adapter<RecyclerGridViewHolder>() {
//        //생성하는부분
//        //var data = listOf<GridData>()
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
//            val binding = RecyclerGridItemBinding.inflate(LayoutInflater.from(context),parent,false)
//            //val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false)
//            return RecyclerGridViewHolder(binding);
//        }
//        //보여줄 개수
//        override fun getItemCount() = data.size
//
//        //수정하는부분
//        override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
//            holder.onBind(data[position])
//
//            //그리드 안 이미지(item) 클릭시 나중에 영상 재생?
//            holder.itemView.setOnClickListener{
//                Toast.makeText(context, data[position].gridTitle, Toast.LENGTH_SHORT).show()
//
//                val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
//                vedioIntent.putExtra("title", data[position].gridTitle)
//                vedioIntent.putExtra("img", data[position].gridImg)
//                vedioIntent.putExtra("url", data[position].gridUrl)
//                startActivity(vedioIntent)
//
//            }
//        }
//    }
}

object BindingAdapter {
    @BindingAdapter("imageUrl","error")
    @JvmStatic
    fun loadImage(imageView: ImageView, url:String, error: Drawable){
        Glide.with(imageView.context).load(url)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
    }
}
