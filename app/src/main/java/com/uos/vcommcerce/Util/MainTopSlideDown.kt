package com.uos.vcommcerce.Util

import android.view.MotionEvent
import android.view.View

class MainTopSlideDown : View.OnClickListener,View.OnTouchListener {

    companion object{
        var instance = MainTopSlideDown()
        //현재 상태 확인
        var state = 0;
    }



    //검색뷰 온클릭 리스터
    val mainTopViewSearchOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            state = 1;

        }
    }



    //드래그용 터치 이벤트 리스너
    val mainTopViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    }
    //드래그용 터치 이벤트 리스너
    val mainTopViewOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {

            }
            return true;
        }
    }



    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}
