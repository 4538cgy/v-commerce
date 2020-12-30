package com.uos.vcommcerce.mainslide.mainbottomslide


import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.adapter.mainActivityState
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.mainslide.maintopslide.MainTopView
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight


class MainBottomView  {
    val BottomMin : Int = 110 //고정값
    var BottomMid : Int = 0
    var BottomMax : Int = 0
    var standardSize_Y : Int = 0

    //메인의 바인딩
    private lateinit var binding: ActivityMainBinding

    companion object{
        var instance = MainBottomView()
        lateinit var BottomView : View
        lateinit var ContentView : View
    }

    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setBottomView(bottomView: View,contentView:View, MainActivity: Activity,Binding: ActivityMainBinding){
        BottomView = bottomView;        //메인뷰 할당
        ContentView = contentView
        SetSize(MainActivity)                   //각 크기 설정
        binding = Binding
        binding.bottomview = this
    }

    //하단바 온클릭 이벤트 리스너 - 하단바 닫기

    fun BottomViewClick(view: View){
        when(mainActivityState){
            MainActivityState.slideUp1->{ BottonViewShow(MainActivityState.default) }
//          MainActivityState.slideUp2->{ BottonViewShow(MainActivityState.default) } //2단확장시 막아두기
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

        Log.d("컨텐츠 : ", "BottomMid" + BottomMid + "BottomMin" + BottomMin + "content : " + (BottomMid-BottomMin-167))


        BottomView.setHeight(BottomMax)     //하단뷰 크기 설정
        ContentView.setHeight(BottomMid-BottomMin-165)
        ViewAnimation(BottomView, 0, BottomMax.dp()-BottomMin.dp(), 0,MainActivityState.default)
    }

}