package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.activity.review.ReviewActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.datamodel.ProductDTO
import com.uos.vcommcerce.datamodel.ProductModel
import com.uos.vcommcerce.mainslide.MainBottomView
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.mainslide.mainActivityState
import com.uos.vcommcerce.profile.UserActivity
import com.uos.vcommcerce.search.SearchFragment
import com.uos.vcommcerce.util.view.ZoomOutPageTransformer
import com.uos.vcommcerce.util.DisplaySize
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.util.dp
import kotlinx.android.synthetic.main.item_exoplayer.view.*
import kotlin.math.abs

var Imm: InputMethodManager? = null;

class MainActivity : BaseActivity<ActivityMainBinding>(
    layoutId = R.layout.activity_main
), SearchFragment.searchEnd {

    //제품리스트
    private val productList: ProductModel by viewModels()
    var productData: MutableLiveData<ArrayList<ProductDTO>> =
        MutableLiveData<ArrayList<ProductDTO>>()

    //접속자 정보
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    //메인에 물려있는 탑과 바텀뷰 + 플레이어
    var MainBottom: MainBottomView = MainBottomView()

    //검색창 프라그먼트
    var SearchFragmentView: SearchFragment = SearchFragment()

    //창크기 정보를 가지는 객체
    lateinit var DisplaySize: ObservableField<DisplaySize>

    companion object {
        var TouchPoint: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DisplaySize = ObservableField(DisplaySize(this))
        binding.mainActivity = this
        binding.productList = productList

        //리스트 변동을 확인할 옵저버 생성 뷰모델의 리스트가바뀌면 확인해서 메인의 리스트를바꾼다음 어댑터에 재할당
        val dataObserver: Observer<ArrayList<ProductDTO>> =
            Observer { livedata ->
                productData.value = livedata
                binding.vpViewpager.adapter = VideoAdapter(this, productData)
            }
        //뷰모델 리스트에 옵저버 장착
        productList.productList.observe(this, dataObserver)

        //아이템 정보 바인딩에 할당
        // 최석우 일시적으로 앱터져서 막음
        //registerPushToken()

        //뷰페이져 어댑터 설정
//        Binding.vpViewpager.adapter = VideoAdapter(this,productList.productList)
        //메인 바텀뷰에 필요한 인자들 전송
        MainBottom.getMainBinding(binding, this)

        //키보드 숨기기위한 시스템 변수
        Imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;


        //비디오 플레이어 설정
        // 스크롤 수평 설정
        binding.vpViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpViewpager.setPageTransformer(ZoomOutPageTransformer())
        //뷰페이저 민감도 조절 코드
        var recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        var recyclerview = recyclerViewField.get(binding.vpViewpager)
        var touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        var touchSlop: Int = touchSlopField.get(recyclerview) as Int
        //민감도를 6으로 설정.
        touchSlopField.set(recyclerview, touchSlop * 6)


        // 뷰페이저 리스너 (ViewPager 1과 다르게 2는 필요한 것만 오버라이딩이 가능하다.
        binding.vpViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 다른 페이지로 스크롤 됬을때 ViewPager 의 현재 페이지 텍스트뷰를 갱신해준다.
                productList.setProduct(position)
                //페이지 이동후 디폴트 타입으로 변경
                returnDefaultView()
            }
        }
        )

        //비디오 플레이어 위치 조정
        ViewAnimation(binding.VideoView, 0, (63 * DisplaySize.get()!!.size_Y).toInt().dp(), 0)

        //검색창 프라그먼트
        supportFragmentManager.beginTransaction().replace(R.id.search_view, SearchFragmentView)
            .commit()
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
        Log.d("뒤로가기 누른거 확인 ", mainActivityState.toString())
        if (mainActivityState == MainActivityState.search) {
            searchEnd()
        } else {
            super.onBackPressed()
        }
    }

    //프로필 이동 이벤트
    fun profileMove(view: View) {
        intent = Intent(this, UserActivity::class.java)
        intent.putExtra("Uid", firebaseAuth.currentUser?.uid)
        startActivity(intent)

    }

    //세팅으로 돌아가는 이벤트
    fun IconMove4(view: View) {
        startActivity(Intent(this, SettingActivity::class.java))
    }


    fun moveProfile(view: View) {
        intent = Intent(this, UserActivity::class.java)
        intent.putExtra("Uid", productList.product.get()?.sellerUid)
        startActivity(intent)
    }


    //검색창 클릭
    fun SearchEvent(view: View) {
        Log.d("검색창 오픈!", "검색창오픈!!")
        //메인 상태를 검색으로 변경
        mainActivityState = MainActivityState.search
        //검색창 띄우기
        binding.searchView.visibility = View.VISIBLE
        //검색창 최초상태로 변환
        SearchFragmentView.SearchSet();
    }

    //리뷰열기
    fun openReview(view: View) {
        var intent = Intent(this, ReviewActivity::class.java)
        intent.apply {
            //임시로 막아둠
//            putExtra("mediaContent",mediaContent.get()?.reviews)
        }
        startActivity(intent)
    }


    inner class VideoAdapter(
        private val context: Context,
        var data: LiveData<ArrayList<ProductDTO>>
    ) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder =
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_exoplayer, parent, false)
            )

        override fun getItemCount(): Int = data.value!!.size
//        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.d("재 바인드? : ", data.value.toString())
            var view = holder.itemView

            var player = SimpleExoPlayer.Builder(context).build()

            //Glide.with(context).load(items[position]).into(holder.imageUrl)
            view.item_exoplayer.player = player
            view.item_exoplayer.hideController()

            player?.setMediaItem(MediaItem.fromUri(data.value!!.get(0)!!.videoList!!.get(0)))
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
                                binding.bottomview?.BottonViewSlideUp(MainActivityState.slideUp)
                            }
                        } else {
                            Log.d("드레그 DOWN : ", "드레그 DOWN")
                            if (mainActivityState == MainActivityState.slideUp) {
                                binding.bottomview?.BottonViewSlideDown(MainActivityState.default)
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

    //기본상태로 돌아가기
    fun returnDefaultView() {
        binding.bottomview?.BottonViewSlideDown(MainActivityState.default)
    }

    //검색 종료
    override fun searchEnd(view: View?) {
        binding.searchView.visibility = View.GONE
        returnDefaultView()
    }
}
