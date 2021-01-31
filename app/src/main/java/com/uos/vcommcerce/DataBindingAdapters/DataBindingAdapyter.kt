package com.uos.vcommcerce.DataBindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.uos.vcommcerce.R

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
    fun setVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }




}