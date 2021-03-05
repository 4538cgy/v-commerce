package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.opengl.Visibility
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginRight
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.activity.review.ReviewActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.*
import com.uos.vcommcerce.datamodel.ObservableProductDTO
import com.uos.vcommcerce.datamodel.ProductDTO
import com.uos.vcommcerce.profile.UserActivity
import com.uos.vcommcerce.profile.VideoGridFragment
import com.uos.vcommcerce.search.SearchFragment
import com.uos.vcommcerce.tranformer.ZoomOutPageTransformer
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.activity_main.*
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
    var MainBottom : MainBottomView = MainBottomView()

    var SearchFragmentView : SearchFragment = SearchFragment()

    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375

    //플레이어 크기
    var PlayerSize : Int = 560

    //탑뷰 크기
    val TopViewSize : Int = 55

    //피그마크기1px 당 실제뷰 크기값
    var size_Y :Float = 0f
    var size_X :Float = 0f

    //파이어 베이스에서 데이터를 불러옴
    init {
        Log.d("생명주기 : ","init 시작");

        firestore.collection("product").document("productInfo").collection("normalProduct")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                Log.d("생명주기 : ","파베 시작");

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
                Log.d("생명주기 : ","파베 종료");

            }

        Log.d("생명주기 : ","init 종료");
    }

    companion object {
        var TouchPoint: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("생명주기 : ","onCreate 시작");

        Binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Binding.mainActivity = this
        //아이템 정보 바인딩에 할당
        Binding.mediaContent = mediaContent

        // 최석우 일시적으로 앱터져서 막음
        //registerPushToken()

        //뷰페이져 어댑터 설정
        Binding.vpViewpager.adapter = VideoAdapter(this)

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

        //피그마크기1px 당 실제뷰 크기값
        var size_Y = (ScreenSize.y / density)/standardSize_Y
        var size_X = (ScreenSize.x / density)/standardSize_X




        //탑뷰 크기 설정

        //아이콘레이아웃 크기설정
        Binding.mainViewChange.setHeight((size_Y*TopViewSize).toInt())

        //각 아이템 크기 성정
        //moveItem1 => activityMainImageButtonProfile
        Binding.activityMainImagebuttonProfile.setHeight((size_Y*TopViewSize).toInt())
        Binding.activityMainImagebuttonProfile.setWidth((size_Y*TopViewSize).toInt())

        Binding.activityMainImagebuttonVideoList.setHeight((size_Y*TopViewSize).toInt())
        Binding.activityMainImagebuttonVideoList.setWidth((size_Y*TopViewSize).toInt())

        Binding.activityMainImagebuttonCart.setHeight((size_Y*TopViewSize).toInt())
        Binding.activityMainImagebuttonCart.setWidth((size_Y*TopViewSize).toInt())

        //movewItem4 => activityMainImageButtonSetting
        Binding.activityMainImagebuttonSearch.setHeight((size_Y*TopViewSize).toInt())
        Binding.activityMainImagebuttonSearch.setWidth((size_Y*TopViewSize).toInt())

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
        Binding.VideoView.setHeight((size_Y*PlayerSize).toInt())
        Binding.sellerImg.setHeight((size_Y*60).toInt())
        Binding.sellerImg.setWidth((size_Y*60).toInt())
        Binding.likeBtn.setHeight((size_Y*48).toInt())
        Binding.likeBtn.setWidth((size_Y*48).toInt())

        //비디오 플레이어 마진 설정
        var lp1 = Binding.VideoView.layoutParams as ConstraintLayout.LayoutParams
        lp1?.let {
            lp1.rightMargin = (30*size_X).toInt().dp()
            lp1.leftMargin = (30*size_X).toInt().dp()
            Binding.VideoView.layoutParams = lp1
        }

        var lp2 = Binding.sellerImg.layoutParams as ConstraintLayout.LayoutParams
        lp2?.let {
            lp2.rightMargin = (6*size_X).toInt().dp()
            lp2.bottomMargin = (6*size_X).toInt().dp()
            Binding.sellerImg.layoutParams = lp2
        }

        var lp3 = Binding.likeBtn.layoutParams as ConstraintLayout.LayoutParams
        lp3?.let {
            lp3.rightMargin = (12*size_X).toInt().dp()
            lp3.bottomMargin = (12*size_X).toInt().dp()
            Binding.likeBtn.layoutParams = lp3
        }

        //비디오 플레이어 위치 조정
        ViewAnimation(Binding.VideoView, 0, (63*size_Y).toInt().dp(), 0)


        //검색창 프라그먼트
        supportFragmentManager.beginTransaction().replace(R.id.search_view, SearchFragmentView).commit()

        Log.d("생명주기 : ","onCreate 종료");

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

    //검색중에 백키누르면 검색종료 하도록 기능변경
    override fun onBackPressed() {
        if(mainActivityState == MainActivityState.search){
            //검색창 닫기
            Binding.searchView.visibility = View.GONE
            returnDefaultView()
        }else {
            super.onBackPressed()
        }
    }

    //상단뷰 1번아이콘 클릭이벤트
    fun IconMove1(view : View) { startActivity(Intent(this, UserActivity::class.java)) }
    //4번 아이콘
    fun IconMove4(view : View) {startActivity(Intent(this, SettingActivity::class.java))}


    //검색창 클릭
    fun SearchEvent(view: View){
        Log.d("검색창 오픈!","검색창오픈!!")
        //메인 상태를 검색으로 변경
        mainActivityState = MainActivityState.search
        //검색창 띄우기
        Binding.searchView.visibility = View.VISIBLE
        //검색창 최초상태로 변환
        SearchFragmentView.SearchSet();
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
                            if (mainActivityState == MainActivityState.default) {
                                    Binding.bottomview?.BottonViewSlideUp(MainActivityState.slideUp)
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            if (mainActivityState == MainActivityState.slideUp) {
                                    Binding.bottomview?.BottonViewSlideDown(MainActivityState.default)
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
        //검색중일때 작용
//        if (mainActivityState == MainActivityState.search) {
//            Binding.topview?.SearchEnd()
//            Imm?.hideSoftInputFromWindow(Binding.mainSearch.windowToken, 0);
//        }
        Binding.bottomview?.BottonViewSlideDown(MainActivityState.default)
    }





}
