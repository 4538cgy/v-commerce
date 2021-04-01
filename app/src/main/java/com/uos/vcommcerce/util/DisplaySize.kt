package com.uos.vcommcerce.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import androidx.databinding.ObservableField

class DisplaySize (var activity : Activity) {

    //화면의 픽셀
    var screenWidthPixel :Int =0;
    var screenHeightPixel :Int =0;
    var density = Resources.getSystem().displayMetrics.density;

    //피그마 기준 값
    var standardSize_Y : Int = 750
    var standardSize_X : Int = 375

    //피그마크기1px 당 실제뷰 크기값
    var size_Y : Float =0f
    var size_X : Float =0f


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

    }

}