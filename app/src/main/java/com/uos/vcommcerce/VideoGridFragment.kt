package com.uos.vcommcerce

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_video_grid.*
import kotlinx.android.synthetic.main.recycler_grid_item.view.*

class VideoGridFragment : Fragment() {

    val DataList = arrayListOf(
        GridData(R.drawable.ic_launcher_background, "0번"),
        GridData(R.drawable.ic_launcher_background, "1번"),
        GridData(R.drawable.btn_signin_facebook, "2번"),
        GridData(R.drawable.ic_launcher_background, "3번"),
        GridData(R.drawable.ic_launcher_background, "4번"),
        GridData(R.drawable.ic_launcher_background, "5번"),
        GridData(R.drawable.com_facebook_auth_dialog_cancel_background, "6번"),
        GridData(R.drawable.ic_launcher_background, "7번"),
        GridData(R.drawable.ic_launcher_background, "8번"),
        GridData(R.drawable.com_facebook_button_icon_white, "9번"),
        GridData(R.drawable.ic_launcher_background, "10번"),
        GridData(R.drawable.com_facebook_tooltip_black_xout, "11번"),
        GridData(R.drawable.common_google_signin_btn_text_disabled, "12번"),
        GridData(R.drawable.ic_launcher_background, "13번"),
        GridData(R.drawable.ic_launcher_background, "14번"),
        GridData(R.drawable.messenger_bubble_large_white, "15번"),
        GridData(R.drawable.ic_launcher_background, "16번"),
        GridData(R.drawable.messenger_button_white_bg_selector, "17번"),
        GridData(R.drawable.ic_launcher_background, "18번"),
        GridData(R.drawable.ic_launcher_background, "19번"),
        GridData(R.drawable.ic_launcher_background, "20번"),
        GridData(R.drawable.ic_launcher_background, "21번"),
        GridData(R.drawable.ic_launcher_background, "22번"),
        GridData(R.drawable.ic_launcher_background, "23번"),
        GridData(R.drawable.ic_launcher_background, "24번")
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
    inner class GridData(val img:Int, val title:String)


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
            }
        }
    }

}
