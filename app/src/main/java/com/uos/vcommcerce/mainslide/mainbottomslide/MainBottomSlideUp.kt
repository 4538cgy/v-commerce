package com.uos.vcommcerce.mainslide.mainbottomslide


import android.view.View
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight

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
        if(topBottomState == TopBottomState.none) {

            mainBottomView?.setHeight(BottomMid)
            ViewAnimation(mainBottomView, BottomMid.dp().toFloat() - BottomMin.dp().toFloat(), 0f, 500, TopBottomState.slideUpMid)

        }else if(topBottomState == TopBottomState.slideUpMid){

            mainBottomView?.setHeight(BottomMax)
            ViewAnimation(mainBottomView, BottomMax.dp().toFloat() - BottomMid.dp().toFloat(),0f,500,TopBottomState.slideUpMax)
        }
    }

    //하단바 슬라이드 다운 함수
    fun SlideDown(){
        when(topBottomState){
            TopBottomState.slideUpMid->{
                ViewAnimation(mainBottomView, 0f, BottomMid.dp().toFloat() - BottomMin.dp().toFloat(),500,TopBottomState.none,BottomMin)
            }
            TopBottomState.slideUpMax->{
                ViewAnimation(mainBottomView, 0f, BottomMax.dp().toFloat() - BottomMin.dp().toFloat(),500,TopBottomState.none,BottomMin)
            }
        }

    }

    fun hideView(){
        ViewAnimation(mainBottomView, 0f, BottomMin.dp().toFloat(),500,TopBottomState.notChange)
    }
    fun showView(){
        ViewAnimation(mainBottomView, BottomMin.dp().toFloat(), 0f,500,TopBottomState.notChange)
    }

}