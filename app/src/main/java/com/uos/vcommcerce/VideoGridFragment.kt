package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.TestPackageDeleteSoon.TestExoplayerActivity
import kotlinx.android.synthetic.main.fragment_video_grid.*
import kotlinx.android.synthetic.main.recycler_grid_item.view.*
import kotlinx.android.synthetic.main.test_exoplayer_view.*
private var mediaPath = "gs://sns-kotlin.appspot.com/1분 동안 세계에서 일어나는 일들!!.mp4"
private var mediaPath2 = "https://firebasestorage.googleapis.com/v0/b/sns-kotlin.appspot.com/o/images%2FIMAGE_20200908_182705_.png?alt=media&token=9347344e-6ba8-4231-93a2-8f61593f5c3f"
private var mediaPath3 = "https://www.youtube.com/embed/oYnfsg-l0KM"
private var mediaPath4 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
private var mediaPath5 = "http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8"
private var mediaPath6 = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
class VideoGridFragment : Fragment() {

    val DataList = arrayListOf(
//        GridData(R.drawable.ic_launcher_background, "0번", mediaPath),
//        GridData(R.drawable.ic_launcher_background, "1번",mediaPath2),
//        GridData(R.drawable.btn_signin_facebook, "2번",mediaPath3),
//        GridData(R.drawable.ic_launcher_background, "3번", mediaPath4),
//        GridData(R.drawable.ic_launcher_background, "4번",mediaPath5),
        GridData(R.drawable.ic_launcher_background, "5번",mediaPath6),
     //  GridData(R.drawable.com_facebook_auth_dialog_cancel_background, "강철의 눈물","http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8"),
        GridData(R.drawable.ic_launcher_background, "빅벅 버니",
            "https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8"),
        GridData(R.drawable.ic_launcher_background, "신텔",
            "https://multiplatform-f.akamaihd.net/i/multi/april11/sintel/sintel-hd_,512x288_450_b,640x360_700_b,768x432_1000_b,1024x576_1400_m,.mp4.csmil/master.m3u8"),
        GridData(R.drawable.com_facebook_button_icon_white, "Apple 샘플","http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"),
        GridData(R.drawable.ic_launcher_background, "fMP4","https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8")
     //   GridData(R.drawable.com_facebook_tooltip_black_xout, "라이브 Akamai","https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"),
     //   GridData(R.drawable.common_google_signin_btn_text_disabled, "Dolby Multichgannel","http://d3rlna7iyyu8wu.cloudfront.net/skip_armstrong/skip_armstrong_multichannel_subs.m3u8 ")
//        GridData(R.drawable.ic_launcher_background, "13번"),
//        GridData(R.drawable.ic_launcher_background, "14번"),
//        GridData(R.drawable.messenger_bubble_large_white, "15번"),
//        GridData(R.drawable.ic_launcher_background, "16번"),
//        GridData(R.drawable.messenger_button_white_bg_selector, "17번"),
//        GridData(R.drawable.ic_launcher_background, "18번"),
//        GridData(R.drawable.ic_launcher_background, "19번"),
//        GridData(R.drawable.ic_launcher_background, "20번"),
//        GridData(R.drawable.ic_launcher_background, "21번"),
//        GridData(R.drawable.ic_launcher_background, "22번"),
//        GridData(R.drawable.ic_launcher_background, "23번"),
//        GridData(R.drawable.ic_launcher_background, "24번")
    )

    lateinit var recyclerGridView :RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_video_grid, container, false)
        //리사이클러뷰
        recyclerGridView = fragmentView.findViewById(R.id.recyclerGridView)
        recyclerGridView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, requireContext())

        return fragmentView

    }

    //그리드 데이터
    inner class GridData(val img:Int, val title:String, val videoUrl:String)


    inner class RecyclerGridViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val img = v.content_Img
        val title = v.content_Text
    }

    inner class RecyclerGridViewAdapter(val DataList:ArrayList<GridData>, val context: Context) : RecyclerView.Adapter<RecyclerGridViewHolder>() {
        //생성하는부분
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
            val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_grid_item, parent, false)
            return RecyclerGridViewHolder(cellForRow);
        }
        //보여줄 개수
        override fun getItemCount() = DataList.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
            val data = DataList[position]
            holder.img.setImageResource(DataList[position].img)
            holder.title.text = DataList[position].title

            //그리드 안 이미지(item) 클릭시 나중에 영상 재생?
            holder.itemView.setOnClickListener{
                Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()

                val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
                vedioIntent.putExtra("title", data.title)
                vedioIntent.putExtra("img", data.img)
                vedioIntent.putExtra("url", data.videoUrl)
                startActivity(vedioIntent)

            }
        }
    }

}
