package com.uos.vcommcerce.mainslide

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.model.MediaContentDTO
import com.uos.vcommcerce.model.ProductDTO
import com.uos.vcommcerce.model.UserDTO
import com.uos.vcommcerce.tranformer.ZoomOutPageTransformer
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight
import kotlinx.android.synthetic.main.item_exoplayer.view.*
import kotlin.math.abs


class MainPlayer  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vp_viewpager:ViewPager2
    var firestore = FirebaseFirestore.getInstance()

    var items: ArrayList<MediaContentDTO> = ArrayList<MediaContentDTO>()

    var mediaContent: MediaContentDTO = MediaContentDTO()

    companion object {
        //싱글톤 생성
        var instance = MainPlayer()
        var TouchPoint: Int? = null

    }

    fun setPlayerView(MainActivity: Activity,Binding : ActivityMainBinding,vp_Viewpager:ViewPager2){
        //메인 바인딩을 받아와서 mediaContent 설정
        binding = Binding
        binding.mediaContent = mediaContent

        //뷰페이져를받아와서 설정
        vp_viewpager=vp_Viewpager

        // 스크롤 수직 설정
        // vp_viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // 스크롤 수평 설정
        vp_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vp_viewpager.setPageTransformer(ZoomOutPageTransformer())

        SetSize(MainActivity,vp_Viewpager)

        //뷰페이저 민감도 조절 코드
        // 시작
        var recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true

        var recyclerview = recyclerViewField.get(vp_viewpager)
        var touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true

        var touchSlop: Int = touchSlopField.get(recyclerview) as Int
        //민감도를 6으로 줬습니다.
        touchSlopField.set(recyclerview, touchSlop * 6)
        // 끝

        // 뷰페이저 리스너 (ViewPager 1과 다르게 2는 필요한 것만 오버라이딩이 가능하다.
        vp_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d("TEST onPageScrolled", position.toString())
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 다른 페이지로 스크롤 됬을때 ViewPager 의 현재 페이지 텍스트뷰를 갱신해준다.
                Log.d("TEST onPageSelected", position.toString())
                mediaContent.set(items[position])
                //페이지 이동후 디폴트 타입으로 변경
                returnDefaultView()
            }
        })


        var products : ArrayList<ProductDTO> =  arrayListOf()
        var productsId : ArrayList<String> = arrayListOf()
        firestore.collection("product").document("productInfo").collection("normalProduct").addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            if (querySnapshot == null) {
                Log.d("실패!","실패하엿다..")
                return@addSnapshotListener
            }

            Log.d("성공!","이걸해낸다고??")
            for (snapshot in querySnapshot!!.documents){

                var item = snapshot.toObject(ProductDTO::class.java)
                products.add(item!!)
                productsId.add(snapshot.id)

                //binding.activityMainTextView.text = products.uid.toString()
                //startChangeFunction(products)
            }

            //비동기 스코프 내부
            products.forEach {
                println(it.toString() + "비동기 스코프 밖의 데이터 리스트 product")
            }

            productsId.forEach {

                println(it.toString() + "비동기 스코프 밖의 데이터 리스트 productId")
            }



        }
    }

    init {
        items.add(
            MediaContentDTO(
                "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
                "1 번동영상",
                "1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용 1번 동영상의 내용"
            )
        )
        items.add(
            MediaContentDTO(
                "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
                "2 번동영상",
                "2번 동영상의 내용"
            )
        )
        items.add(
            MediaContentDTO(
                "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
                "3 번동영상",
                "3번 동영상의 내용"
            )
        )
        items.add(
            MediaContentDTO(
                "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
                "4 번동영상",
                "4번 동영상의 내용"
            )
        )
        items.add(
            MediaContentDTO(
                "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
                "5 번동영상",
                "5번 동영상의 내용"
            )
        )
    }

    fun PlayerUp(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(vp_viewpager, 0, 22.dp(), 500,state)
    }

    fun PlayerDown(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(vp_viewpager, 0, 138.dp(), 500,state)
    }


    fun SetSize(MainActivity: Activity,viewPager2: ViewPager2) {
        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        //피그마 기준 값
        var standardSize_Y : Int = 770
        //피그마크기1px 당 실제뷰 크기값
        var size_Y = (ScreenSize.y / density)/standardSize_Y
        //플레이어 크기
        var PlayerSize : Int = 610

        viewPager2.setHeight((size_Y*PlayerSize).toInt())

        ViewAnimation(vp_viewpager, 0, 22.dp(), 500)
    }


        inner class VideoAdapter(private val context: Context): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


        //이미지뷰 터치 시작위치 측정
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //val imageUrl : ImageView = view.findViewById(R.id.iv_image)
            val exoPlayer : View = view.findViewById(R.id.item_exoplayer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exoplayer,parent,false))

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

        private val ViewPageClickListner = View.OnClickListener { }
        private val ViewPageTouchListner = View.OnTouchListener { v, event ->
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
                                //MainActivityState.slideUp1->{MainBottomView.instance.BottonViewSlideUp2(MainActivityState.slideUp2)  }
                                MainActivityState.slideDown1->{
                                    MainTopView.instance.TopViewHide(MainActivityState.default)
                                    MainBottomView.instance.BottonViewShow()
                                    PlayerUp()
                                }
                                //MainActivityState.slideDown2->{MainBottomView.instance.BottonViewShow(MainActivityState.slideDown1)}
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            when(mainActivityState){
                                MainActivityState.default->{
                                    MainTopView.instance.TopViewShow(MainActivityState.slideDown1)
                                    MainBottomView.instance.BottonViewHide()
                                    PlayerDown()
                                }
                                //MainActivityState.slideDown1->{MainBottomView.instance.BottonViewHide(MainActivityState.slideDown2)  }
                                MainActivityState.slideUp1->{MainBottomView.instance.BottonViewShow(MainActivityState.default)  }
                                //2단계확장-> defualt 막음
                                //MainActivityState.slideUp2->{MainBottomView.instance.BottonViewShow(MainActivityState.default)  }
                            }
                        }
                    } else {//터치일시 각 창을 닫음
                        returnDefaultView()
                    }
                }
            }

            false
        }

    }

}