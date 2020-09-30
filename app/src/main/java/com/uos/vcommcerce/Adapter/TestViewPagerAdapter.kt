package com.uos.vcommcerce.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uos.vcommcerce.Imm
import com.uos.vcommcerce.R
import com.uos.vcommcerce.Util.MainBottomSlideUp
import com.uos.vcommcerce.Util.MainTopSlideDown
import com.uos.vcommcerce.Util.TopBottomState
import com.uos.vcommcerce.Util.dp
import com.uos.vcommcerce.isTopViewOpen
import com.uos.vcommcerce.topBottomState
import kotlin.math.abs

class TestViewPagerAdapter(private val context: Context, private val items: ArrayList<String>) : RecyclerView.Adapter<TestViewPagerAdapter.ViewHolder>(){

    //이미지뷰 터치 시작위치 측정
    companion object{
        var TouchPoint: Int? = null
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val imageUrl: ImageView = view.findViewById(R.id.iv_image)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(items[position]).into(holder.imageUrl)

        //뷰에 터치리스너 추가
        holder.itemView.setOnClickListener(ViewPageClickListner)
        holder.itemView.setOnTouchListener(ViewPageTouchListner)
    }



    //최석우 뷰 컨트롤을위한 클릭과 터치리스너
    val ViewPageClickListner =object : View.OnClickListener{ override fun onClick(v: View?) {} }
    val ViewPageTouchListner =object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                //창을 눌럿을떄
                MotionEvent.ACTION_DOWN -> {
                    TouchPoint = event.getY().toInt();
                }
                //움직임이 감지 됫을때
                MotionEvent.ACTION_MOVE -> { }

                //손땟을때
                MotionEvent.ACTION_UP -> {
                    var distance : Int = TouchPoint!!.minus( event.getY().toInt());
                    if (abs(distance)>50){//드래기일시 해당하는 창을 열기
                        if (distance>0){
                            Log.d("드레그 UP : ","드레그 UP")
                            MainBottomSlideUp.instance.SlideUp();
                        }else{
                            Log.d("드레그 DOWN : ","드레그 DOWN")
                            MainTopSlideDown.instance.SlideDown();
                        }
                    }else{//터치일시 각 창을 닫음
                        when(topBottomState){
                            TopBottomState().slideUp1-> MainBottomSlideUp.instance.SlideDown()
                            TopBottomState().slideUp2-> MainBottomSlideUp.instance.SlideDown()
                            TopBottomState().slideDown-> MainTopSlideDown.instance.SlideUp()
                            TopBottomState().search-> {
                                MainTopSlideDown.instance.SearchUp()
                                Imm?.hideSoftInputFromWindow(MainTopSlideDown.MainSearchView?.windowToken,0);
                            }
                        }

                    }
                }
            }

            return false
        }
    }


}