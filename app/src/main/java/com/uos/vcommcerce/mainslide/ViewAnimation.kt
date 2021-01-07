package com.uos.vcommcerce.mainslide

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import com.uos.vcommcerce.Imm
import com.uos.vcommcerce.util.MainActivityState


var mainActivityState = MainActivityState.default;

//위치값은 dp로 받아옴
fun ViewAnimation(TargetView:View, StartPos: Int, EndPos : Int, during : Long, ChangedState : MainActivityState = MainActivityState.notChange) {
//애니메이션 생성
    var distance : Int = StartPos-EndPos
    Log.d("Y이동값 : ","Distance = "+distance)
    Log.d("받아온 change값 : ","ChangedState = "+ChangedState)
    Log.d("뷰 상단 까지의 거리 시작전 : ",""+TargetView.top)
    ObjectAnimator.ofFloat(TargetView,"translationY",-distance.toFloat()).apply{
        duration = during
        mainActivityState = MainActivityState.moving
        start()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if(ChangedState!=MainActivityState.notChange) {
                    mainActivityState = ChangedState
                    Log.d("종료됨  : ", " topBottomState값  : " + mainActivityState)
                    Log.d("뷰 상단 까지의 거리 w종료후 : ",""+TargetView.top)
                }
            }
        })
    }
}

fun returnDefaultView() {
    if (mainActivityState == MainActivityState.search) {
        MainTopView.instance.SearchEnd()
        Imm?.hideSoftInputFromWindow(MainTopView.MainSearchView?.windowToken, 0);
    }
    MainBottomView.instance.BottonViewShow(MainActivityState.default)
    MainTopView.instance.TopViewHide()

}