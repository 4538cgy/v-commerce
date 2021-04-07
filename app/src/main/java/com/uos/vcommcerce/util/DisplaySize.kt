package com.uos.vcommcerce.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import androidx.databinding.ObservableField

class DisplaySize (var activity : Activity) {

    //화면의 픽셀수
    var screenWidthPixel :Int =0;
    var screenHeightPixel :Int =0;
    var density = Resources.getSystem().displayMetrics.density;

    //피그마 기준 값
    var standardSize_Y : Int = 750
    var standardSize_X : Int = 375

    //피그마크기1px 와 실제뷰으dp값 비율
    var size_Y : Float =0f
    var size_X : Float =0f


    //메인 하단뷰 피그마 px
    val standardBottomMin: Int = 112 //고정값
    val standardBottomMid: Int = 400
    //메인 하단뷰 크기
    var BottomMin : Int = 0
    var BottomMid : Int = 0

    init {
        //해상도 측정
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        screenWidthPixel = ScreenSize.x;
        screenHeightPixel = ScreenSize.y
        size_Y = (ScreenSize.y / density)/standardSize_Y
        size_X = (ScreenSize.x / density)/standardSize_X

        //하단뷰 크기 연산
        BottomMin = (size_Y * standardBottomMin).toInt()
        BottomMid = (size_Y * standardBottomMid).toInt()
    }

}