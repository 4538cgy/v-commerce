package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.activity.review.ReviewActivity
import com.uos.vcommcerce.activity.review.ReviewDetailActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.*
import com.uos.vcommcerce.model.ObservableProductDTO
import com.uos.vcommcerce.model.ProductDTO
import com.uos.vcommcerce.tranformer.ZoomOutPageTransformer
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight
import kotlinx.android.synthetic.main.item_exoplayer.view.*
import kotlin.math.abs


var Imm: InputMethodManager? = null;


class MainActivity : AppCompatActivity() {

    //메인 엑티비티에 물려있는 바인딩
     private lateinit var Binding: ActivityMainBinding

    //파이어 스토어 객체
    var firestore = FirebaseFirestore.getInstance()
    //상품 정보 리스트
    var items: ArrayList<ProductDTO> = arrayListOf()
    //현재 아이템 정보
    var mediaContent: ObservableProductDTO = ObservableProductDTO(ProductDTO())

    //메인에 물려있는 탑과 바텀뷰 + 플레이어
    var MainTop : MainTopView = MainTopView()
    var MainBottom : MainBottomView = MainBottomView()

    //파이어 베이스에서 데이터를 불러옴
    init {
        firestore.collection("product").document("productInfo").collection("normalProduct")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (querySnapshot == null) {
                    return@addSnapshotListener
                }

                items.clear()
                for (snapshot in querySnapshot!!.documents) {

                    var item = snapshot.toObject(ProductDTO::class.java)
                    Log.d("ItemInfo", "아이템정보 " + item)
                    if (item != null) {
                        items.add(item!!)
                    }
                }
                Binding.vpViewpager.adapter?.notifyDataSetChanged()
            }
    }

    companion object {
        var TouchPoint: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Binding.mainActivity = this
        //아이템 정보 바인딩에 할당
        Binding.mediaContent = mediaContent

        // 최석우 일시적으로 앱터져서 막음
        //registerPushToken()

        //뷰페이져 어댑터 설정
        Binding.vpViewpager.adapter = VideoAdapter(this)

        //탑뷰의 서치뷰에 어뎁터 추가후 탑뷰에 전송
        MainTop.getMainActivity(Binding, this)

        //메인 바텀뷰에 필요한 인자들 전송
        MainBottom.getMainBinding(Binding, this)
        Log.d("메인 ", "넘어감??")

        //키보드 숨기기위한 시스템 변수
        Imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;

        //해상도 측정
        val display = this.windowManager.defaultDisplay
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

        
        
        //비디오 플레이어 설정

        // 스크롤 수평 설정
        Binding.vpViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        Binding.vpViewpager.setPageTransformer(ZoomOutPageTransformer())
        //뷰페이저 민감도 조절 코드
        var recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        var recyclerview = recyclerViewField.get(Binding.vpViewpager)
        var touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        var touchSlop: Int = touchSlopField.get(recyclerview) as Int
        //민감도를 6으로 설정.
        touchSlopField.set(recyclerview, touchSlop * 6)


        // 뷰페이저 리스너 (ViewPager 1과 다르게 2는 필요한 것만 오버라이딩이 가능하다.
        Binding.vpViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    Log.d("onPageScrolled", "onPageScrolled")
                }
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // 다른 페이지로 스크롤 됬을때 ViewPager 의 현재 페이지 텍스트뷰를 갱신해준다.
                    mediaContent.set(items[position])
                    //페이지 이동후 디폴트 타입으로 변경
                    returnDefaultView()
                }
            }
        )

        //비디오 플레이어 크기 설정
        Binding.vpViewpager.setHeight((size_Y*PlayerSize).toInt())
    }

//최석우 앱터져서 일시적으로 막음
//    fun registerPushToken(){
//
//        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
//                task ->
//            val token = task.result?.token
//            val uid = FirebaseAuth.getInstance().currentUser?.uid
//            val map = mutableMapOf<String,Any>()
//            map["pushToken"] = token!!
//            FirebaseFirestore.getInstance().collection("pushtokens").document(uid!!).set(map)
//        }
//
//    }

    override fun onStop() {
        super.onStop()
        //4538cgy@gmail.com UID 값 [ 너무 푸쉬를 많이 보내서 일시적으로 사용 중지 주석 풀지마세요! ]
        //FcmPush.instance.sendMessage("IIBpkwk5jUSNDa0qnDZxgwEvq812", "hi", "bye")
    }

    fun openReview(view:View){
        var intent = Intent(this, ReviewActivity::class.java)
        intent.apply {
            putExtra("mediaContent",mediaContent.productTile)
        }
        startActivity(intent)
    }


    inner class VideoAdapter(private val context: Context) :
        RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

        //이미지뷰 터치 시작위치 측정
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder =
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_exoplayer, parent, false)
            )

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var view = holder.itemView

            var player = SimpleExoPlayer.Builder(context).build()

            //Glide.with(context).load(items[position]).into(holder.imageUrl)
            var mediaItem = items[position]
            view.item_exoplayer.player = player
            view.item_exoplayer.hideController()

            player?.setMediaItem(MediaItem.fromUri(mediaItem.videoUri as String))
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
                            when (mainActivityState) {
                                MainActivityState.default -> {
                                    Binding.bottomview?.BottonViewSlideUp1(MainActivityState.slideUp1)
                                }
                                MainActivityState.slideDown1 -> {
                                    Binding.topview?.TopViewHide(MainActivityState.default)
                                    Binding.bottomview?.BottonViewShow()
                                    PlayerUp()
                                }
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            when (mainActivityState) {
                                MainActivityState.default -> {
                                    Binding.topview?.TopViewShow(MainActivityState.slideDown1)
                                    Binding.bottomview?.BottonViewHide()
                                    PlayerDown()
                                }

                                MainActivityState.slideUp1 -> {
                                    Binding.bottomview?.BottonViewShow(MainActivityState.default)
                                }

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

    fun returnDefaultView() {
        if (mainActivityState == MainActivityState.search) {
            Binding.topview?.SearchEnd()
            Imm?.hideSoftInputFromWindow(Binding.mainSearch.windowToken, 0);
        }
        Binding.bottomview?.BottonViewShow(MainActivityState.default)
        PlayerUp()
        Binding.topview?.TopViewHide()

    }

    //비디오 플레이어 조정 함수
    fun PlayerUp(state: MainActivityState = MainActivityState.notChange) {
        ViewAnimation(Binding.vpViewpager, 0, 22.dp(), 500, state)
    }
    fun PlayerDown(state: MainActivityState = MainActivityState.notChange) {
        ViewAnimation(Binding.vpViewpager, 0, 138.dp(), 500, state)
    }



}
