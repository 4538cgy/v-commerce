package com.uos.vcommcerce.DataBindingAdapters

import android.app.Activity
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.facebook.internal.Mutable
import com.uos.vcommcerce.R
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight

object DataBindingAdapyter {

    //이미지 추가용
    @JvmStatic
    @BindingAdapter("SelectItem")
    fun SelectItem(view: ImageView, isVisible: Boolean) {
        if (isVisible) {
            view.setBackgroundResource(R.drawable.background_round_gray_10dp_black_strock)
        } else {
            view.setBackgroundResource(R.drawable.background_round_gray_10dp)
        }
    }

    //뷰보이기 안보이기
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, isVisible: String) {
        if (isVisible.equals("VISIBLE")) {
            view.visibility = View.VISIBLE
        } else if (isVisible.equals("INVISIBLE")) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    //뷰높이 조정
    @JvmStatic
    @BindingAdapter("layout_height", "stand_size")
    fun setViewHeight(view: View, isheight: Int, ysize: ObservableField<Float>) {

        if (isheight != null && ysize != null) {

            Log.d("뷰 높이 변경", "isheight : " + isheight + "ysize : " + ysize.get())
            var size = (isheight * ysize.get()!!).toInt().dp()

            val lp = view.layoutParams
            lp?.let {
                lp.height = size;
                view.layoutParams = lp
            }
            Log.d("뷰 높이 변경", view.id.toString() + "높이 변경 _ " + "size : " + size)
            view.viewTreeObserver.removeOnGlobalLayoutListener { this }
        }

    }

    //제품 가격 표기
    @JvmStatic
    @BindingAdapter("productPrice")
    fun setPrice(view: TextView, priceInfo: MutableMap<String,Long>) {
        if(priceInfo["price"] != null) {
            Log.d("가격정보 ",priceInfo.toString())
            //할인 정보가 있을경우
            if (priceInfo["miners"]!! < 0 || priceInfo["persent"]!! > 0) {
                view.text = priceInfo["salePrice"].toString() + "원"
            } else {      //할인 정보가 없을경우
                view.text = priceInfo["price"].toString() + "원"
            }
        }
    }

    //제품 할인가 표기
    @JvmStatic
    @BindingAdapter("productSalePrice")
    fun setSalePrice(view: TextView, priceInfo: MutableMap<String,Long>) {
        if(priceInfo["price"] != null) {
            Log.d("가격정보 ",priceInfo.toString())
            //할인 정보가 있을경우
            if (priceInfo["miners"]!! < 0 || priceInfo["persent"]!! > 0) {
                //취소선 적용
                view.setPaintFlags(0x10)
                view.text = priceInfo["price"].toString() + "원"
            } else {      //할인 정보가 없을경우
                view.text = ""
            }
        }
    }

    //제품 할인 정보 표기
    @JvmStatic
    @BindingAdapter("productSaleInfo")
    fun setSaleInfo(view: View, priceInfo: MutableMap<String,Long>) {

    }


}