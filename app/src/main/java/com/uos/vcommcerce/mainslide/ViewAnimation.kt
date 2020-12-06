package com.uos.vcommcerce.mainslide

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.util.setHeight

fun ViewAnimation(TargetView:View?, StartPos: Float, EndPos : Float, duration : Long, ChangedState : TopBottomState = TopBottomState.notChange, ChangedViewSize:Int=-1) {
//애니메이션 생성
    var animate: TranslateAnimation = TranslateAnimation(0f, 0f, StartPos, EndPos)
    animate.duration = duration; //애니메이션 동작시간
    animate.fillAfter = true;   //애니메이션후 상태 유지
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            // 뷰이동이 끝난다음에 크기가 바껴야할때
            if (ChangedViewSize != -1) {
                topBottomState = TopBottomState.moving

                animate = TranslateAnimation(0f, 0f, 0f, 0f)
                animate.duration = 0;
                animate.fillAfter = true;
                animate.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        if (topBottomState == TopBottomState.moving) {
                            topBottomState = ChangedState
                        }
                    }
                })
                TargetView?.setHeight(ChangedViewSize)
                TargetView?.startAnimation(animate)
            } else if ((ChangedState!= TopBottomState.notChange).and(topBottomState == TopBottomState.moving)) {
                topBottomState = ChangedState
            }
        }
    })
    //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 파라매터값으로 변경
    //애니메이션 실행 -> 종료후 뷰 크기 변경

    topBottomState = TopBottomState.moving
    TargetView?.startAnimation(animate)
}