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
import com.uos.vcommcerce.R
import com.uos.vcommcerce.mainslide.mainbottomslide.MainBottomView
import com.uos.vcommcerce.mainslide.maintopslide.MainTopView
import com.uos.vcommcerce.model.MediaContentDTO
import com.uos.vcommcerce.util.MainActivityState
import kotlinx.android.synthetic.main.item_exoplayer.view.*
import kotlin.math.abs


var mainActivityState = MainActivityState.default;


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
                            Log.d("드레그 UP : ", "드레그 UP")
                            when(mainActivityState){
                                MainActivityState.default->{ MainBottomView.instance.BottonViewSlideUp1(MainActivityState.slideUp1) }
                                //2단계 확장 막음
//                                MainActivityState.slideUp1->{MainBottomView.instance.BottonViewSlideUp2(MainActivityState.slideUp2)  }
                                MainActivityState.slideDown1->{MainTopView.instance.TopViewHide(MainActivityState.default)}
                                MainActivityState.slideDown2->{MainBottomView.instance.BottonViewShow(MainActivityState.slideDown1)}
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            when(mainActivityState){
                                MainActivityState.default->{MainTopView.instance.TopViewShow(MainActivityState.slideDown1) }
                                MainActivityState.slideDown1->{MainBottomView.instance.BottonViewHide(MainActivityState.slideDown2)  }
                                MainActivityState.slideUp1->{MainBottomView.instance.BottonViewShow(MainActivityState.default)  }
                                //2단계확장-> defualt 막음
//                              MainActivityState.slideUp2->{MainBottomView.instance.BottonViewShow(MainActivityState.default)  }
                            }
                        }
                    } else {//단순터치시 기본상태로 돌아감
                        returnDefaultView()
                    }
                }
            }
            return false
        }
    }
}

//페이지이동시 원래 상태로 되돌아가기
fun returnDefaultView() {
    if (mainActivityState == MainActivityState.search) {
        Log.d("cLEAR","포커스제거!")
        MainTopView.instance.SearchEnd()
    }
    MainBottomView.instance.BottonViewShow(MainActivityState.default)
    MainTopView.instance.TopViewHide()

}