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
    var standardSize_Y: Int = 770
    var standardSize_X: Int = 375
    val standardBottomMin: Int = 130 //고정값
    var standardBottomMid: Int = 327

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
        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density
        size_Y = (ScreenSize.y / density) / standardSize_Y
        size_X = (ScreenSize.x / density) / standardSize_X
        BottomMin = (size_Y * standardBottomMin).toInt()
        BottomMid = (size_Y * standardBottomMid).toInt()


        //해상도에 맞게 뷰크기랑 마진 조정
        //하단뷰 크기 설정
        Binding.mainBottomView.setHeight(BottomMid)
        Binding.mainBottomView.setPadding(24 * size_X.toInt().dp(), 0, 24 * size_X.toInt().dp(), 0)

        //해상도에 맞게 뷰크기랑 마진 조정
        //닉네임 크기 설정
        Binding.nickname.setTextHeight((size_Y * 18).toInt())
        Binding.nickname.setTextSize((size_Y * 12).toInt().toFloat())
        Binding.nickname.setMarginBottom((size_Y * 4).toInt())
        Binding.nickname.setMarginTop((size_Y * 16).toInt())

        //상품명 크기 설정
        Binding.title.setTextHeight((size_Y * 22).toInt())
        Binding.title.setTextSize((size_Y * 16).toInt().toFloat())
        Binding.title.setMarginBottom((size_Y * 8).toInt())

        //주소와 가격뷰의 그륩 크기설정
        Binding.bottomgroup1.setHeight((size_Y * 53).toInt())

        //주소 크기 설정
        Binding.address.setTextHeight((size_Y * 18).toInt())
        Binding.address.setTextSize((size_Y * 12).toInt().toFloat())

        //가격 크기 설정
        Binding.price.setTextHeight((size_Y * 30).toInt())
        Binding.price.setTextSize((size_Y * 20).toInt().toFloat())

        //본문 크기 설정
        Binding.content.setTextHeight((size_Y * 60).toInt())
        Binding.content.setTextSize((size_Y * 15))
        Binding.content.setMarginBottom((size_Y * 24).toInt())

        //리뷰 크기 설정
        Binding.review.setButtonHeight((size_Y * 22).toInt())
        Binding.review.setTextSize((size_Y * 16).toInt().toFloat())
        Binding.review.setMarginBottom((size_Y * 24).toInt())

        //좋아요와 옵션선택의 그륩 크기설정
        Binding.bottomgroup2.setHeight((size_Y * 76).toInt())

        Binding.like.setButtonHeight((size_Y * 40).toInt())
        Binding.like.setTextSize((size_Y * 22).toInt().toFloat())

        Binding.selectoption.setButtonHeight((size_Y * 40).toInt())
        Binding.selectoption.setTextSize((size_Y * 22).toInt().toFloat())
    }


//    //메인의 바인딩을 가져오기
//    fun getMainBinding(bottomView: View,nicknameview:TextView,titleview:TextView,addressview:TextView,priceview:TextView,contentview:TextView,reviewview:Button,likeView: Button,bottomgroup1 : LinearLayout,bottomgroup2 : LinearLayout,selectoption:Button, MainActivity: Activity,Binding: ActivityMainBinding){
//        BottomView = bottomView;        //메인뷰 할당
//        SetSize(MainActivity,nicknameview,titleview,addressview,priceview,contentview,reviewview,likeView,bottomgroup1,bottomgroup2,selectoption)                   //각 크기 설정
//        binding = Binding
//        binding.bottomview = this
//    }

    //하단바 온클릭 이벤트
    fun BottomViewClick(view: View) {
        when (mainActivityState) {
            MainActivityState.slideUp1 -> {
                BottonViewShow(MainActivityState.default)
            }
        }
    }


    //하단 바텀뷰 보이게 하는 함수
    fun BottonViewShow(state: MainActivityState = MainActivityState.notChange) {
        Log.d("쇼 실행", "쇼!")
        ViewAnimation(Binding.mainBottomView, 0, BottomMid.dp() - BottomMin.dp(), 500, state)
    }

    //하단 바텀뷰 숨기는 함수
    fun BottonViewHide(state: MainActivityState = MainActivityState.notChange) {
        Log.d("하이드 실행", "하이드!")
        ViewAnimation(Binding.mainBottomView, 0, BottomMid.dp(), 500, state)
    }

    //하단 바텀뷰 확장하는 함수
    fun BottonViewSlideUp1(state: MainActivityState = MainActivityState.notChange) {
        Log.d("업 실행", "업!")
        ViewAnimation(Binding.mainBottomView, 0, 0, 500, state)
    }


}
