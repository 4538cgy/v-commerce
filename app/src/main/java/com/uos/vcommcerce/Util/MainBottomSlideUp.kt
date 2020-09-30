package com.uos.vcommcerce.Util


import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.uos.vcommcerce.isBottomViewOpen
import com.uos.vcommcerce.topBottomState
import javax.annotation.meta.When

class MainBottomSlideUp  {
    val BottomMin : Int = 80;
    val BottomMid : Int = 300;
    val BottomMax : Int = 700;

    companion object{
        var instance = MainBottomSlideUp()
        //기본크기 밖으로 창을 키운적 있는지 체크 변수
        var moveCheck = false;
        var mainBottomView : View? = null
        var topView: View? = null;
    }

    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setBottomView(MainBottomView: View){
        mainBottomView = MainBottomView;
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
        if((topBottomState == TopBottomState().none).or(topBottomState == TopBottomState().slideDown).or(topBottomState == TopBottomState().search)) {

            //다른 상태일때 해당상태 종료시키기
            if (topBottomState == TopBottomState().slideDown){
                 //상단바 닫기
                MainTopSlideDown.instance.SlideUp();
            }else if(topBottomState == TopBottomState().search){
                //검색창 닫기
            }

            //애니메이션 생성
            val animate: TranslateAnimation = TranslateAnimation(0f, 0f, BottomMid.dp().toFloat() - BottomMin.dp().toFloat(), 0f)
            animate.duration = 500; //애니메이션 동작시간
            animate.fillAfter = true;   //애니메이션후 상태 유지

            //애니메이션 리스너 장착
            animate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    //애니메종료후 상태변경
                    topBottomState = TopBottomState().slideUp1
                }
            })

            //상태를 무빙으로 변경 -> 종료후 slideUp2
            topBottomState = TopBottomState().moving;

            //뷰크기를 변경후 애니메이션 실행
            mainBottomView?.setHeight(BottomMid)
            mainBottomView?.startAnimation(animate)

        }else if(topBottomState == TopBottomState().slideUp1){

            //상단뷰 숨기기기
            MainTopSlideDown.instance.TopViewClose()
            //애니메이션 생성
            val animate: TranslateAnimation =
                TranslateAnimation(0f, 0f, BottomMax.dp().toFloat() - BottomMid.dp().toFloat(), 0f)
            animate.duration = 500; //애니메이션 동작시간
            animate.fillAfter = true;   //애니메이션후 상태 유지

            animate.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    //애니메종료후 상태변경
                    topBottomState = TopBottomState().slideUp2
                }
            })

            //상태를 무빙으로 변경 -> 종료후 slideUp2
            topBottomState = TopBottomState().moving;

            //뷰크기를 변경후 애니메이션 실행
            mainBottomView?.setHeight(BottomMax)
            mainBottomView?.startAnimation(animate)
        }
    }

    //하단바 슬라이드 다운 함수
    fun SlideDown(){
        when(topBottomState){
            TopBottomState().slideUp1->{

                //애니메이션 생성
                val animate: TranslateAnimation = TranslateAnimation(0f, 0f, 0f, BottomMid.dp().toFloat() - BottomMin.dp().toFloat())
                animate.duration = 500; //애니메이션 동작시간
                animate.fillAfter = true;   //애니메이션후 상태 유지

                animate.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) { }
                    override fun onAnimationEnd(animation: Animation?) {
                        //slideUp1 상태일매나 상태 none 설정
                        if(topBottomState == TopBottomState().moving) {
                            topBottomState = TopBottomState().none
                        }
                        //태초 상태로 돌아가는 애니메이션
                        val animate: TranslateAnimation = TranslateAnimation(0f, 0f, 0f, 0f)
                        animate.duration = 0;
                        animate.fillAfter = true;
                        mainBottomView?.setHeight(BottomMin)
                        mainBottomView?.startAnimation(animate)
                    }
                })

                //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 none
                topBottomState = TopBottomState().moving
                //애니메이션 실행 -> 종료후 뷰 크기 변경
                mainBottomView?.startAnimation(animate)
            }
            TopBottomState().slideUp2->{

                //상단바 다시 열기
                MainTopSlideDown.instance.TopViewOpen()
                //애니메이션 생성
                val animate: TranslateAnimation = TranslateAnimation(0f, 0f, 0f, BottomMax.dp().toFloat() - BottomMin.dp().toFloat())
                animate.duration = 500;
                animate.fillAfter = true;

                animate.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        //종료후 상태 변경
                        topBottomState = TopBottomState().none
                        //태초 상태로 돌아가는 애니메이션
                        val animate: TranslateAnimation = TranslateAnimation(0f, 0f, 0f, 0f)
                        animate.duration = 0;
                        animate.fillAfter = true;
                        mainBottomView?.setHeight(BottomMin)
                        mainBottomView?.startAnimation(animate)
                    }

                })

                //상태를 무빙으로 변경 -> 종료후 none
                topBottomState = TopBottomState().moving
                //애니메이션 실행 -> 종료후 뷰 크기 변경
                mainBottomView?.startAnimation(animate)
            }
        }

    }


}