package com.uos.vcommcerce.mainslide


import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.util.*


class MainBottomView  {

    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375
    val standardBottomMin : Int = 130 //고정값
    var standardBottomMid : Int = 327

    //피그마크기1px 당 실제뷰 크기값
    var size_X : Float = 0f
    var size_Y : Float = 0f

    //실제뷰 크기값
    var BottomMin : Int = 0
    var BottomMid : Int = 0


    //메인의 바인딩
    private lateinit var binding: ActivityMainBinding

    companion object{
        var instance = MainBottomView()
        lateinit var BottomView : View
    }
    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setBottomView(bottomView: View,nicknameview:TextView,titleview:TextView,addressview:TextView,priceview:TextView,contentview:TextView,reviewview:Button,likeView: Button,bottomgroup1 : LinearLayout,bottomgroup2 : LinearLayout,selectoption:Button, MainActivity: Activity,Binding: ActivityMainBinding){
        BottomView = bottomView;        //메인뷰 할당
        SetSize(MainActivity,nicknameview,titleview,addressview,priceview,contentview,reviewview,likeView,bottomgroup1,bottomgroup2,selectoption)                   //각 크기 설정
        binding = Binding
        binding.bottomview = this
    }

    //하단바 온클릭 이벤트 리스너 - 하단바 닫기

    fun BottomViewClick(view: View){
        when(mainActivityState){
//            MainActivityState.slideUp1->{ BottonViewShow(MainActivityState.default) }
//          MainActivityState.slideUp2->{ BottonViewShow(MainActivityState.default) } //2단확장시 막아두기
        }
    }


    fun BottonViewShow(state: MainActivityState = MainActivityState.notChange ) {
        Log.d("쇼 실행","쇼!")
        ViewAnimation(BottomView, 0, BottomMid.dp()-BottomMin.dp(), 500,state)
    }

    fun BottonViewHide(state: MainActivityState = MainActivityState.notChange ) {
        Log.d("하이드 실행","하이드!")
        ViewAnimation(BottomView, 0, BottomMid.dp(), 500,state)
    }

    fun BottonViewSlideUp1(state: MainActivityState = MainActivityState.notChange ) {
        Log.d("업 실행","업!")
        ViewAnimation(BottomView, 0, 0, 500,state)
    }


    //해상도에 맞는 크기 측정
    fun SetSize(MainActivity: Activity,nicknameview:TextView,titleview:TextView,addressview:TextView,priceview:TextView,contentview:TextView,reviewview:Button,likeView: Button,bottomgroup1 : LinearLayout,bottomgroup2 : LinearLayout,selectoption:Button){
        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        size_Y = (ScreenSize.y / density)/standardSize_Y
        size_X = (ScreenSize.x / density)/standardSize_X
        BottomMin = (size_Y*standardBottomMin).toInt()
        BottomMid = (size_Y*standardBottomMid).toInt()
        //해상도에 맞게 뷰크기랑 마진 조정
        BottomView.setHeight(BottomMid)     //하단뷰 크기 설정
        BottomView.setPadding(24*size_X.toInt().dp(),0,24*size_X.toInt().dp(),0)



        nicknameview.setTextHeight((size_Y*18).toInt())
        nicknameview.setTextSize((size_Y*12).toInt().toFloat())
        nicknameview.setMarginBottom((size_Y*4).toInt())
        nicknameview.setMarginTop((size_Y*16).toInt())

        titleview.setTextHeight((size_Y*22).toInt())
        titleview.setTextSize((size_Y*16).toInt().toFloat())
        titleview.setMarginBottom((size_Y*8).toInt())

        bottomgroup1.setHeight((size_Y*53).toInt())

        addressview.setTextHeight((size_Y*18).toInt())
        addressview.setTextSize((size_Y*12).toInt().toFloat())

        priceview.setTextHeight((size_Y*30).toInt())
        priceview.setTextSize((size_Y*20).toInt().toFloat())

        contentview.setTextHeight((size_Y*60).toInt())
        contentview.setTextSize((size_Y*15))
        contentview.setMarginBottom((size_Y*24).toInt())

        reviewview.setButtonHeight((size_Y*22).toInt())
        reviewview.setTextSize((size_Y*16).toInt().toFloat())
        reviewview.setMarginBottom((size_Y*24).toInt())

        bottomgroup2.setHeight((size_Y*76).toInt())

        likeView.setButtonHeight((size_Y*40).toInt())
        likeView.setTextSize((size_Y*22).toInt().toFloat())

        selectoption.setButtonHeight((size_Y*40).toInt())
        selectoption.setTextSize((size_Y*22).toInt().toFloat())

        Log.d("최초 실행","최초!")
        ViewAnimation(BottomView, 0, BottomMid-BottomMin, 0,MainActivityState.default)
    }



}