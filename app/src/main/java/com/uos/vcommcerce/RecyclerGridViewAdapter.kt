package com.uos.vcommcerce

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.recycler_grid_item.view.*

//그리드 데이터
class GridData(val img:Int, val title:String)


class RecyclerGridViewHolder(v : View) : RecyclerView.ViewHolder(v){

    val img = v.content_Img
    val title = v.content_Text
}

class RecyclerGridViewAdapter(val DataList:ArrayList<GridData>, val context: Context) : RecyclerView.Adapter<RecyclerGridViewHolder>() {
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