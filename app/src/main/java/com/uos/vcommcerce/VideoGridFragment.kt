package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.databinding.FragmentVideoGridBinding
import com.uos.vcommcerce.databinding.RecyclerGridItemBinding
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import kotlinx.android.synthetic.main.fragment_video_grid.*
import kotlinx.android.synthetic.main.recycler_grid_item.view.*

private var mediaPath = "gs://sns-kotlin.appspot.com/1분 동안 세계에서 일어나는 일들!!.mp4"
private var mediaPath2 = "https://firebasestorage.googleapis.com/v0/b/sns-kotlin.appspot.com/o/images%2FIMAGE_20200908_182705_.png?alt=media&token=9347344e-6ba8-4231-93a2-8f61593f5c3f"
private var mediaPath3 = "https://www.youtube.com/embed/oYnfsg-l0KM"
private var mediaPath4 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
private var mediaPath5 = "http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8"
private var mediaPath6 = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"

data class GridData(
    var gridImg:Int,
    var gridTitle:String,
    var gridUrl:String
)
class VideoGridFragment : Fragment() {

    val DataList = listOf(
        GridData(R.drawable.ic_launcher_background, "5번",mediaPath6),
        GridData(R.drawable.ic_launcher_background, "빅벅 버니",
            "https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8"),
        GridData(R.drawable.ic_launcher_background, "신텔",
            "https://multiplatform-f.akamaihd.net/i/multi/april11/sintel/sintel-hd_,512x288_450_b,640x360_700_b,768x432_1000_b,1024x576_1400_m,.mp4.csmil/master.m3u8"),
        GridData(R.drawable.com_facebook_button_icon_white, "Apple 샘플","http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"),
        GridData(R.drawable.ic_launcher_background, "fMP4","https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"),
        GridData(R.drawable.ic_launcher_background, "3번", mediaPath4),
        GridData(R.drawable.com_facebook_tooltip_black_xout, "라이브 Akamai","https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")
    )

    lateinit var binding:FragmentVideoGridBinding
    //lateinit var recyclerGridView :RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val fragmentView = inflater.inflate(R.layout.fragment_video_grid, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_video_grid, container,false)
        binding.recyclerGridView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, requireContext())

        val fragmentView = binding.root


        //리사이클러뷰
        //recyclerGridView = fragmentView.findViewById(R.id.recyclerGridView)
        //recyclerGridView.layoutManager = GridLayoutManager(requireContext(), 3)
        //recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, requireContext())

        return fragmentView

    }

    //그리드 데이터
    //inner class GridData(val img:Int, val title:String, val videoUrl:String)


    inner class RecyclerGridViewHolder(val binding: RecyclerGridItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : GridData){
            binding.griditem = data
        }
    }

    inner class RecyclerGridViewAdapter(var data:List<GridData>, val context: Context) : RecyclerView.Adapter<RecyclerGridViewHolder>() {
        //생성하는부분
        //var data = listOf<GridData>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
            val binding = RecyclerGridItemBinding.inflate(LayoutInflater.from(context),parent,false)
            //val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false)
            return RecyclerGridViewHolder(binding);
        }
        //보여줄 개수
        override fun getItemCount() = data.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
            holder.onBind(data[position])

            //그리드 안 이미지(item) 클릭시 나중에 영상 재생?
            holder.itemView.setOnClickListener{
                Toast.makeText(context, data[position].gridTitle, Toast.LENGTH_SHORT).show()

                val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
                vedioIntent.putExtra("title", data[position].gridTitle)
                vedioIntent.putExtra("img", data[position].gridImg)
                vedioIntent.putExtra("url", data[position].gridUrl)
                startActivity(vedioIntent)

            }
        }
    }

}
