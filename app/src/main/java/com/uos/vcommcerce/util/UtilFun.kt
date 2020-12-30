package com.uos.vcommcerce.util

import android.content.res.Resources
import android.util.TypedValue
import android.view.View


    //2020/9/17 최석우 int값 dp로 변환하는 함수
     fun Int.dp(): Int { //함수 이름도 직관적으로 보이기 위해 dp()로 바꿨습니다.
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }

    fun Int.intTodP(): Int {
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


