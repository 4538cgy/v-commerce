package com.uos.vcommcerce.mainslide


import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.util.*


class MainBottomView {

    //피그마 기준 값

    val standardBottomMin: Int = 112 //고정값
    var standardBottomMid: Int = 400

    //피그마크기1px 당 실제뷰 크기값
    var size_X: Float = 0f
    var size_Y: Float = 0f

    //실제뷰 크기값
    var BottomMin: Int = 0
    var BottomMid: Int = 0


    //메인의 바인딩
    private lateinit var Binding: ActivityMainBinding
    private lateinit var MainActivity: Activity

    //메인의 바인딩 가져오기
    fun getMainBinding(binding: ActivityMainBinding, mainActivity: Activity) {
        //메인엑티비티뷰 바인드 캐싱후 자신을 연결하기
        Binding = binding
        Binding.bottomview = this
        //메인엑티비티 캐싱하기
        MainActivity = mainActivity

        //메인바인드를가져왓다 -> 메인 뷰 를가져왓다고 이해가능
        //메인뷰의 크기를 피그마와 동일하도록 바꿈

        size_Y = Binding.mainActivity!!.displaySize.get()!!.size_Y
        size_X = Binding.mainActivity!!.displaySize.get()!!.size_X
        BottomMin = (size_Y * standardBottomMin).toInt()
        BottomMid = (size_Y * standardBottomMid).toInt()


        //해상도에 맞게 뷰크기랑 마진 조정
        //하단뷰 크기 설정
        Binding.mainBottomView.setPadding(24 * size_X.toInt().dp(), 0, 24 * size_X.toInt().dp(), 0)


    }


//    //메인의 바인딩을 가져오기
//    fun getMainBinding(bottomView: View,nicknameview:TextView,titleview:TextView,addressview:TextView,priceview:TextView,contentview:TextView,reviewview:Button,likeView: Button,bottomgroup1 : LinearLayout,bottomgroup2 : LinearLayout,selectoption:Button, MainActivity: Activity,Binding: ActivityMainBinding){
//        BottomView = bottomView;        //메인뷰 할당
//        SetSize(MainActivity,nicknameview,titleview,addressview,priceview,contentview,reviewview,likeView,bottomgroup1,bottomgroup2,selectoption)                   //각 크기 설정
//        binding = Binding
//        binding.bottomview = this
//    }

    //하단바 온클릭 이벤트 (디폴트로)
    fun BottomViewClick(view: View) {
        if (mainActivityState == MainActivityState.slideUp) {
            BottonViewSlideDown(MainActivityState.default)
        }
    }


    //하단 바텀뷰 보이게 하는 함수
    fun BottonViewSlideDown(state: MainActivityState = MainActivityState.notChange) {
        ViewAnimation(Binding.mainBottomView, 0, BottomMid.dp() - BottomMin.dp(), 500, state)
    }

    //하단 바텀뷰 확장하는 함수
    fun BottonViewSlideUp(state: MainActivityState = MainActivityState.notChange) {
        ViewAnimation(Binding.mainBottomView, 0, 0, 500, state)
    }


}
