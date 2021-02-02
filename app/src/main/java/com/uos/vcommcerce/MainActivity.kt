package com.uos.vcommcerce

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.MainBottomView
import com.uos.vcommcerce.mainslide.MainPlayer
import com.uos.vcommcerce.mainslide.MainTopView
import kotlinx.android.synthetic.main.activity_main.*



var Imm: InputMethodManager? = null;


class MainActivity : AppCompatActivity() /*, TextView.OnEditorActionListener*/ {

    //메인 엑티비티에 물려있는 바인딩
     private lateinit var binding: ActivityMainBinding

    //메인에 물려있는 탑과 바텀뷰 + 플레이어
    var MainTop : MainTopView = MainTopView()
    var MainBottom : MainBottomView = MainBottomView()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 최석우 일시적으로 앱터져서 막음
        //registerPushToken()

        var videoAdapter : MainPlayer.VideoAdapter = MainPlayer.instance.VideoAdapter(this)
        vp_viewpager.adapter = videoAdapter

        //비디오플레이어에
        MainPlayer.instance.setPlayerView(this, binding,vp_viewpager)


        //탑뷰의 서치뷰에 어뎁터 추가후 탑뷰에 전송
        var adapter: MainTopView.SearchAdapter = MainTopView.instance.SearchAdapter(this)
        mainSearchListView.adapter = MainTopView.instance.SearchAdapter(this)

        //메인 탑뷰에 필요한 인자들 전송
        var ViewList: ArrayList<ImageView> = ArrayList<ImageView>()
        ViewList.add(moveItem1)
        ViewList.add(moveItem2)
        ViewList.add(moveItem3)
        ViewList.add(moveItem4)
        MainTopView.instance.setTopView(mainTopView, mainSearchView, mainSearchListView, mainViewChange,ViewList, adapter, this, binding);

        //메인 바텀뷰에 필요한 인자들 전송
        MainBottom.getMainBinding(binding,this)


        //키보드 숨기기위한 시스템 변수
        Imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;


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


}
