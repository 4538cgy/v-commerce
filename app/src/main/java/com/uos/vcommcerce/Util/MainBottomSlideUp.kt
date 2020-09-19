package com.uos.vcommcerce.Util

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.uos.vcommcerce.isBottomViewOpen

class MainBottomSlideUp : View.OnClickListener,View.OnTouchListener {


    companion object{
        var instance = MainBottomSlideUp()
        //기본크기 밖으로 창을 키운적 있는지 체크 변수
        var moveCheck = false;
    }

     val mainBottomViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    }

     val mainBottomViewOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                //창을 눌럿을떄
                MotionEvent.ACTION_DOWN -> {
                    //창을 눌럿을시 값 초기화
                    moveCheck = false
                }
                //움직임이 감지 됫을때
                MotionEvent.ACTION_MOVE -> {
                    //창이 작을때 드래그 기능 사용
                    if(isBottomViewOpen == false) {

                        //이동값 + 현재뷰 크기로 바뀌어야할 뷰 크기 계산
                        var h = -event.getY().toInt().intTodP() + v?.height!!.intTodP();
                        //뷰 크기 변화
                        //기본 뷰 크기 밖으로 이동한"적"이 잇을시 moveCheck true로 바꾸기
                        if (h >= 80 && h <= 300) {
                            moveCheck = true;
                            v?.setHeight(h);
                        }else if(h<80){
                            v?.setHeight(80);
                        }
                    }
                }
                //손땟을때
                MotionEvent.ACTION_UP -> {
                    //창이 닫겨있을때
                    if (isBottomViewOpen == false) {
                        if ((v?.height?.intTodP()==80).and(moveCheck == false)) {
                            //단순 터치한 경우      - 화면확대 열림처리
                            v?.setHeight(300);
                            isBottomViewOpen = true;
                        }else if ((v?.height?.intTodP()!=80).and(moveCheck == true)){
                            //드래그로 이동한 경우   - 열림처리       이동할떄마다 크기가 변햇음으로 크기변화처리x
                            isBottomViewOpen = true;
                        }else if((v?.height?.intTodP() == 80).and(moveCheck == true)){
                            //드래그로 이동햇다 닫은경우  - 닫은처리   해당부분은 없어도 되나 해당 이벤트가 있다는것을 명시적으로 표기하기위해 작성
                            isBottomViewOpen = false;
                        }
                    }else{//창이 열려있을때
                        isBottomViewOpen = false;
                        v?.setHeight(80);
                    }
                }
            }

            return false;
        }
    }


    //2020/9/17 최석우 int값 dp로 변환하는 함수
    public fun Int.dp(): Int { //함수 이름도 직관적으로 보이기 위해 dp()로 바꿨습니다.
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics)
            .toInt()
    }
    public fun Int.intTodP() : Int{
        val density = Resources.getSystem().displayMetrics.density
        return this.div(density).toInt();
    }


    //2020/9/17 최석우 뷰 크기 변환함수
    fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value.dp();
            layoutParams = lp
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}