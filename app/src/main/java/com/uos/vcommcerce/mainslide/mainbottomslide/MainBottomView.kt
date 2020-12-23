package com.uos.vcommcerce.mainslide.mainbottomslide


import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.adapter.mainActivityState
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.mainslide.maintopslide.MainTopView
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight


class MainBottomView  {
    val BottomMin : Int = 130 //고정값
    var BottomMid : Int = 0
    var BottomMax : Int = 0
    var standardSize_Y : Int = 0
    companion object{
        var instance = MainBottomView()
        lateinit var BottomView : View
    }

    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setBottomView(bottomView: View, MainActivity: Activity){
        BottomView = bottomView;        //메인뷰 할당
        SetSize(MainActivity)                   //각 크기 설정

    }

    //하단바 온클릭 이벤트 리스너 - 하단바 닫기
     val mainBottomViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            when(mainActivityState){
                MainActivityState.slideUp1->{ BottonViewShow(MainActivityState.default) }
                MainActivityState.slideUp2->{ BottonViewShow(MainActivityState.default) }
            }
        }
    }

    fun BottonViewShow(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(BottomView, 0, BottomMax.dp()-BottomMin.dp(), 500,state)
    }

    fun BottonViewHide(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(BottomView, 0, BottomMax.dp(), 500,state)
    }

    fun BottonViewSlideUp1(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(BottomView, 0, BottomMax.dp() - BottomMid.dp(), 500,state)
    }


    fun BottonViewSlideUp2(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(BottomView,0,  0, 500, state)
    }


    //해상도에 맞는 크기 측정
    fun SetSize(MainActivity: Activity){
        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        standardSize_Y = (ScreenSize.y / density).toInt()
        BottomMax = (standardSize_Y*0.9).toDouble().toInt()
        BottomMid = (standardSize_Y*0.45).toDouble().toInt()

        Log.d("체크 : ", "BottomMax" + BottomMax + "BottomMid" + BottomMid)
        Log.d("체크Dp : ", "BottomMax" + BottomMax.dp() + "BottomMid" + BottomMid.dp())

        BottomView.setHeight(BottomMax)     //하단뷰 크기 설정
        BottonViewShow(MainActivityState.default)
    }

}