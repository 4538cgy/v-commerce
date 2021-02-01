package com.uos.vcommcerce.DataBindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.uos.vcommcerce.R
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight

object DataBindingAdapyter {

    //이미지 추가용
    @JvmStatic
    @BindingAdapter("SelectItem")
    fun SelectItem(view: ImageView,  isVisible: Boolean) {
        if(isVisible){
            view.setBackgroundResource(R.drawable.background_round_gray_10dp_black_strock)
        }else{
            view.setBackgroundResource(R.drawable.background_round_gray_10dp)
        }
    }

    //뷰보이기 안보이기
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, isVisible: String) {
        if (isVisible.equals("VISIBLE")) {
            view.visibility = View.VISIBLE
        }else if(isVisible.equals("INVISIBLE")) {
            view.visibility = View.INVISIBLE
        }else{
            view.visibility = View.GONE
        }
    }

    //뷰높이 조정
    @JvmStatic
    @BindingAdapter("viewheight")
    fun setViewHeight(view: View, isheight : Int) {

        val lp = view.layoutParams
        lp?.let {
            lp.height = isheight.dp();
            view.layoutParams = lp
        }
    }




}