package com.uos.vcommcerce.mainslide.mainbottomslide


import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight


class MainBottomSlideUp  {
    val BottomMin : Int = 130 //고정값
    var BottomMid : Int = 0
    var BottomMax : Int = 0
    var standardSize_Y : Int = 0
    companion object{
        var instance = MainBottomSlideUp()
        lateinit var mainBottomView : View
    }

    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setBottomView(MainBottomView: View, MainActivity: Activity){
        mainBottomView = MainBottomView;        //메인뷰 할당
        SetSize(MainActivity)                   //각 크기 설정

    }

    //하단바 온클릭 이벤트 리스너 - 하단바 닫기
     val mainBottomViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            SlideDown()
        }
    }

    //하단바 슬라이드 업 함수
    fun SlideUp(){
        //기본상태 or 상단바열려있을시 상닫바 닫기 + 슬라이드 업 하기
        if(topBottomState == TopBottomState.none) {//기본 -> 1단
            ViewAnimation(mainBottomView, BottomMid.dp().toFloat() - BottomMin.dp().toFloat(), 0f, 500,TopBottomState.slideUpMid)
        }else if(topBottomState == TopBottomState.slideUpMid){//1단 -> 2단
            ViewAnimation(mainBottomView, BottomMax.dp().toFloat() - BottomMid.dp().toFloat(), 0f, 500, TopBottomState.slideUpMax)
        }
    }

    //하단바 슬라이드 다운 함수
    fun SlideDown(){


        if(topBottomState == TopBottomState.slideUpMid) {//기본 -> 1단
            ViewAnimation(mainBottomView, 0f, BottomMid.dp().toFloat() - BottomMin.dp().toFloat(), 500, TopBottomState.none)
        }else if(topBottomState == TopBottomState.slideUpMax){//1단 -> 2단
            ViewAnimation(mainBottomView, 0f, BottomMax.dp().toFloat() - BottomMin.dp().toFloat(), 500, TopBottomState.none)
        }

    }

    //하단뷰 숨기기
    fun hideView(){
        ViewAnimation(mainBottomView, 0f, BottomMin.dp().toFloat(), 500, TopBottomState.notChange)
    }
    //하단뷰 보이기
    fun showView(){
        ViewAnimation(mainBottomView, BottomMin.dp().toFloat(), 0f, 500, TopBottomState.notChange)
    }

    //해상도에 맞는 크기 측정
    fun SetSize(MainActivity: Activity){
        val display = (MainActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        standardSize_Y = (ScreenSize.y / density).toInt()
        BottomMax = (standardSize_Y*0.9).toDouble().toInt()
        BottomMid = (standardSize_Y*0.45).toDouble().toInt()

        Log.d("체크 : ", "BottomMax" + BottomMax + "BottomMid" + BottomMid)
        Log.d("체크Dp : ", "BottomMax" + BottomMax.dp() + "BottomMid" + BottomMid.dp())

        mainBottomView.setHeight(BottomMax)     //하단뷰 크기 설정

        mainBottomView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                var left = mainBottomView.getLeft();
                var top = (standardSize_Y-BottomMin).dp()
                var right = mainBottomView.getRight();
                var bottom = (standardSize_Y+BottomMax-BottomMin).dp()
                mainBottomView.layout(left, top, right, bottom);
//                Log.d("크기 Int", " standardSize_Y : " + standardSize_Y + " BottomMax : " + BottomMax +" BottomMid" + BottomMid + " BottomMin"+BottomMin)
//                Log.d("크기 Dp", " standardSize_Y : " + standardSize_Y.dp() + " BottomMax : " + BottomMax.dp() +" BottomMid" + BottomMid.dp() + " BottomMin"+BottomMin.dp())
//                Log.d("레이아웃", " top : " + top + " bottom : " + bottom +" height" + mainBottomView.height)
            }
        })
    }

}