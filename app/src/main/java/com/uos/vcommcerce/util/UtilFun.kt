package com.uos.vcommcerce.util

import android.app.ActionBar
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop


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

fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value.dp();
        layoutParams = lp
    }
}

fun TextView.setTextHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value.dp();
        layoutParams = lp
    }
}

fun Button.setButtonHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value.dp();
        layoutParams = lp
    }
}

fun View.setMarginLeft(value: Int) {
    val lp = this.layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.leftMargin = value.dp()
        layoutParams = lp
    }
}

fun View.setMarginTop(value: Int) {
    val lp = this.layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.topMargin = value.dp()
        layoutParams = lp
    }
}

fun View.setMarginRight(value: Int) {
    val lp = this.layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.rightMargin = value.dp()
        layoutParams = lp
    }
}


fun View.setMarginBottom(value: Int) {
    val lp = this.layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.bottomMargin = value.dp()
        layoutParams = lp
    }
}


