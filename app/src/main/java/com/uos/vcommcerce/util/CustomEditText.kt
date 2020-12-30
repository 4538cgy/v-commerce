package com.uos.vcommcerce.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowId
import android.widget.EditText


@SuppressLint("AppCompatCustomView")
class CustomEditText : EditText {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) :super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    private lateinit var callback : () -> Unit

    fun setCallback(callback: () -> Unit){
        this.callback = callback
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {

        Log.d("TEST","keyCode: ${keyCode} | event: ${event?.action}")
        this.callback()
        return super.onKeyPreIme(keyCode, event)
    }

    //포커스 되엇을때 클릭 이벤트도 실행되도록 커스텀(정상적이면 터치시 포커스이벤트만실행되고 재클릭시 클릭이벤트실행)
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if(focused){
            callOnClick()
        }
    }

}