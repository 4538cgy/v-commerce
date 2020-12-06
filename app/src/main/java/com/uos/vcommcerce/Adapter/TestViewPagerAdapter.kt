package com.uos.vcommcerce.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.uos.vcommcerce.Imm
import com.uos.vcommcerce.R
import com.uos.vcommcerce.mainslide.mainbottomslide.MainBottomSlideUp
import com.uos.vcommcerce.mainslide.maintopslide.MainTopSlideDown
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.model.MediaContentDTO
import com.uos.vcommcerce.topBottomState
import kotlinx.android.synthetic.main.item_exoplayer.view.*
import kotlin.math.abs

class TestViewPagerAdapter(private val context: Context, private val items: ArrayList<MediaContentDTO>) : RecyclerView.Adapter<TestViewPagerAdapter.ViewHolder>(){



    //이미지뷰 터치 시작위치 측정
    companion object{
        var TouchPoint: Int? = null
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val imageUrl : ImageView = view.findViewById(R.id.iv_image)
        val exoPlayer : View = view.findViewById(R.id.item_exoplayer)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        //ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exoplayer,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        var player = SimpleExoPlayer.Builder(context).build()

        //Glide.with(context).load(items[position]).into(holder.imageUrl)
        var mediaItem = items[position]
        view.item_exoplayer.player = player
        view.item_exoplayer.hideController()
        view.item_exoplayer.setOnTouchListener{
                _, motionEvent ->

            when (motionEvent?.action) {
                //창을 눌럿을떄
                MotionEvent.ACTION_DOWN -> {
                    TouchPoint = motionEvent.getY().toInt();
                }
                //움직임이 감지 됫을때
                MotionEvent.ACTION_MOVE -> {
                }

                //손땟을때
                MotionEvent.ACTION_UP -> {
                    var distance: Int = TouchPoint!!.minus(motionEvent.getY().toInt());
                    if (abs(distance) > 50) {//드래기일시 해당하는 창을 열기
                        if (distance > 0) {
                            Log.d("드레그 UP : ", "드레그 UP")
                            MainBottomSlideUp.instance.SlideUp();
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            MainTopSlideDown.instance.SlideDown();
                        }
                    } else {//터치일시 각 창을 닫음
                        returnDefaultView()
                    }
                }
            }

            return@setOnTouchListener false
        }


        player?.setMediaItem(MediaItem.fromUri(mediaItem.url.get().toString()))
        player?.prepare()
        player?.play()




        //뷰에 터치리스너 추가

        holder.itemView.setOnClickListener(ViewPageClickListner)
        holder.itemView.setOnTouchListener(ViewPageTouchListner)


    }





    //최석우 뷰 컨트롤을위한 클릭과 터치리스너

    val ViewPageClickListner = object : View.OnClickListener{ override fun onClick(v: View?) {} }


    val ViewPageTouchListner = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                //창을 눌럿을떄
                MotionEvent.ACTION_DOWN -> {
                    TouchPoint = event.getY().toInt();
                }
                //움직임이 감지 됫을때
                MotionEvent.ACTION_MOVE -> {
                }

                //손땟을때
                MotionEvent.ACTION_UP -> {
                    var distance: Int = TouchPoint!!.minus(event.getY().toInt());
                    if (abs(distance) > 50) {//드래기일시 해당하는 창을 열기
                        if (distance > 0) {
                            Log.d("드레그 UP : ", "드레그 UP 상태 : " + topBottomState )
                            if((topBottomState== TopBottomState.none).or((topBottomState==TopBottomState.slideUpMid))){
                                MainBottomSlideUp.instance.SlideUp();
                            }else if(topBottomState== TopBottomState.slideDown){
                                MainTopSlideDown.instance.SlideUp();
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN 상태 : " + topBottomState)
                            if((topBottomState== TopBottomState.slideUpMid).or((topBottomState==TopBottomState.slideUpMax))){
                                MainBottomSlideUp.instance.SlideDown();
                            }else if(topBottomState== TopBottomState.none){
                                MainTopSlideDown.instance.SlideDown();
                            }
                        }
                    } else {//터치일시 각 창을 닫음
                        returnDefaultView()
                    }
                }
            }

            return false
        }
    }

}

fun returnDefaultView(){
    when (topBottomState) {
        TopBottomState.slideUpMid -> MainBottomSlideUp.instance.SlideDown()
        TopBottomState.slideUpMax -> MainBottomSlideUp.instance.SlideDown()
        TopBottomState.slideDown -> MainTopSlideDown.instance.SlideUp()
        TopBottomState.search -> {
            MainTopSlideDown.instance.SearchUp()
            Imm?.hideSoftInputFromWindow(MainTopSlideDown.MainSearchView?.windowToken, 0);
        }
    }
}