package com.uos.vcommcerce.Util

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
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

}