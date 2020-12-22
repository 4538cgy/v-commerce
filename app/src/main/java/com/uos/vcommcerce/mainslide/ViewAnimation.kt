package com.uos.vcommcerce.mainslide

import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight
import java.security.AccessController.getContext
//위치값은 dp로 받아옴
fun ViewAnimation(TargetView:View, StartPos: Int, EndPos : Int, duration : Long, ChangedState : TopBottomState = TopBottomState.notChange):TranslateAnimation {
//애니메이션 생성
    var distance : Int = StartPos-EndPos
    var animate: TranslateAnimation = TranslateAnimation(0f, 0f,0f, -distance.toFloat())
    animate.duration = duration; //애니메이션 동작시간
    animate.isFillEnabled = true;   //애니메이션후 상태 유지
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            topBottomState = ChangedState
            //레이아운 이동후 재전개
            var left : Int = TargetView.getLeft();
            var top : Int = TargetView.getTop() - (distance);
            var right : Int = TargetView.getRight();
            var bottom : Int = TargetView.getBottom() - (distance);
            Log.d("이동거리 체크","시작"+ StartPos+"종료"+EndPos+"이동거리"+distance)
            //이동한만큼 변화값을 주어야함
            TargetView.layout(left, top , right, bottom);
            Log.d("결과 확인 "," TargetView.getTop() : " +TargetView.getTop()+ " TargetView.getBottom() : " + TargetView.getBottom())
        }
    })

    Log.d("시작 확인 "," TargetView.getTop() : " +TargetView.getTop()+ " TargetView.getBottom() : " + TargetView.getBottom())
    //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 파라매터값으로 변경
    //애니메이션 실행 -> 종료후 뷰 크기 변경
    topBottomState = TopBottomState.moving
    TargetView?.startAnimation(animate)
    return animate;
}