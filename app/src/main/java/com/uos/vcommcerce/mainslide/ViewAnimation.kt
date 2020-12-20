package com.uos.vcommcerce.mainslide

import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.util.setHeight
import java.security.AccessController.getContext

fun ViewAnimation(TargetView:View, StartPos: Float, EndPos : Float, duration : Long, ChangedState : TopBottomState = TopBottomState.notChange):TranslateAnimation {
//애니메이션 생성
    var animate: TranslateAnimation = TranslateAnimation(0f, 0f, StartPos, EndPos)
    animate.duration = duration; //애니메이션 동작시간
    animate.isFillEnabled = true;   //애니메이션후 상태 유지
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            topBottomState = ChangedState
            //레이아운 이동후 재전개
            var left = TargetView.getLeft();
            var top = TargetView.getTop();
            var right = TargetView.getRight();
            var bottom = TargetView.getBottom();
            var distance = StartPos-EndPos
            Log.d("이동거리 체크","시작"+ StartPos+"종료"+EndPos+"이동거리"+distance)
            Log.d("절대위치확인","left"+ left+"top"+top+"right"+right+"bottom"+bottom)
            //이동한만큼 변화값을 주어야함
            TargetView.layout(left, top , right, bottom);
        }
    })


    Log.d("뷰크기 ",""+TargetView.height)
    Log.d("시작 위치 ",""+StartPos)
    Log.d("종료 위치 ",""+EndPos)
    
    //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 파라매터값으로 변경
    //애니메이션 실행 -> 종료후 뷰 크기 변경
    topBottomState = TopBottomState.moving
    TargetView?.startAnimation(animate)
    return animate;
}