package com.uos.vcommcerce.bindingAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.profile.UserActivity
import com.uos.vcommcerce.util.*

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
    fun setViewHeight(view: View, isHeight: Int, display: ObservableField<DisplaySize>) {

        if (isHeight != null && display != null) {
            var Ysize = display.get()!!.size_Y
            var size = (isHeight * Ysize).toInt().dp()

            val lp = view.layoutParams
            lp?.let {
                lp.height = size;
                view.layoutParams = lp
            }
        }
    }

    //뷰넓이 조정
    @JvmStatic
    @BindingAdapter("layout_width", "stand_size")
    fun setViewWidth(view: View, isWidth: Int, display: ObservableField<DisplaySize>) {

        if (isWidth != null && display != null) {
            var Xsize = display.get()!!.size_X
            var size = (isWidth * Xsize).toInt().dp()

            val lp = view.layoutParams
            lp?.let {
                lp.width = size;
                view.layoutParams = lp
            }
        }
    }

    //뷰크기(높이 넓이 일정하게) 조정 (Y기준)
    @JvmStatic
    @BindingAdapter("layout_sizeY", "stand_size")
    fun setViewSizeY(view: View, isSize: Int, display: ObservableField<DisplaySize>) {

        if (isSize != null && display != null) {
            var Ysize = display.get()!!.size_Y
            var size = (isSize * Ysize).toInt().dp()

            val lp = view.layoutParams
            lp?.let {
                lp.height = size;
                lp.width = size;
                view.layoutParams = lp
            }
        }
    }

    //뷰크기(높이 넓이 일정하게) 조정 (X기준)
    @JvmStatic
    @BindingAdapter("layout_sizeX", "stand_size")
    fun setViewSizeX(view: View, isSize: Int, display: ObservableField<DisplaySize>) {

        if (isSize != null && display != null) {
            var Xsize = display.get()!!.size_X
            Log.d("뷰 크기 변경", "isSize : " + isSize + "Xsize : " + Xsize)
            var size = (isSize * Xsize).toInt().dp()

            val lp = view.layoutParams
            lp?.let {
                lp.height = size;
                lp.width = size;
                view.layoutParams = lp
            }
            Log.d("뷰 크기 변경", view.id.toString() + "크기 변경 _ " + "size : " + size)
        }
    }

    //뷰마진 설정
    @JvmStatic
    @BindingAdapter("stand_size_margin","latout_margin_left","latout_margin_top","latout_margin_right","latout_margin_bottom")
    fun setViewMargin(view: View, display: ObservableField<DisplaySize>, margin_left : Int, margin_top : Int, margin_right : Int, margin_bottom : Int) {
        if (display != null) {

            if(view.layoutParams is LinearLayout.LayoutParams){
                val lp = view.layoutParams as LinearLayout.LayoutParams
                lp?.let {
                    lp.leftMargin = (margin_left*display.get()!!.size_X).toInt().dp()
                    lp.topMargin = (margin_top*display.get()!!.size_Y).toInt().dp()
                    lp.rightMargin = (margin_right*display.get()!!.size_X).toInt().dp()
                    lp.bottomMargin = (margin_bottom*display.get()!!.size_Y).toInt().dp()
                    view.layoutParams = lp
                }

            }else if(view.layoutParams is ConstraintLayout.LayoutParams){
                val lp = view.layoutParams as ConstraintLayout.LayoutParams
                lp?.let {
                    lp.leftMargin = (margin_left*display.get()!!.size_X).toInt().dp()
                    lp.topMargin = (margin_top*display.get()!!.size_Y).toInt().dp()
                    lp.rightMargin = (margin_right*display.get()!!.size_X).toInt().dp()
                    lp.bottomMargin = (margin_bottom*display.get()!!.size_Y).toInt().dp()
                    view.layoutParams = lp
                }
            }

            Log.d("뷰 마진 변경", view.id.toString() + "마진 변경" )

        }
    }


    //글자 크기 조정 (높이 넓이 일정하게) 조정 (Y기준)
    @JvmStatic
    @BindingAdapter("text_size", "stand_size_text")
    fun setTestSizeY(view: TextView, isSize: Int, display: ObservableField<DisplaySize>) {

        if (isSize != null && display != null) {
            var Ysize = display.get()!!.size_Y
            var size = (isSize * Ysize)
            view.setTextSize(size)
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
    fun setSaleInfo(view: TextView, priceInfo: MutableMap<String,Long>) {
        if(priceInfo["price"] != null) {
            Log.d("가격정보 ",priceInfo.toString())
            //할인 정보가 있을경우
            if (priceInfo["miners"]!! < 0) {
                view.text = priceInfo["miners"].toString()
            }else if(priceInfo["persent"]!! > 0){
                view.text = "-" + priceInfo["persent"].toString() + "%"
            }else {      //할인 정보가 없을경우
                view.text = ""
            }
        }
    }


    //평점 출력F
    @JvmStatic
    @BindingAdapter("totalRating")
    fun setTotalRating(view: TextView, totalRating: Float) {
        if (totalRating == null) {
            view.text = "평균 0점"
        } else {
            view.text = "평균 " + totalRating + "점"
        }
    }


    //평점 출력
    @JvmStatic
    @BindingAdapter("ratingCount")
    fun setRatingCount(view: TextView, ratingCount: Long) {
        if (ratingCount == null) {
            view.text = "총 0건"
        } else {
            view.text = "총 " + ratingCount + "건"
        }
    }

    //uri로 이미지 추가
    @JvmStatic
    @BindingAdapter("imageUri")
    fun setimageUri(view: ImageView, uri: Uri) {
        view.setImageURI(uri);
    }


    //비디오 플레이 영상준비
    @JvmStatic
    @BindingAdapter("prepareVideo","getcontext")
    fun prepareVideolist(view: PlayerView,urilist: ArrayList<String>,context: Context) {
        var player = SimpleExoPlayer.Builder(context).build()
        view.player = player
        view.hideController()
        view.player!!.setMediaItem(MediaItem.fromUri(urilist.get(0)))
        view.player!!.prepare();
        player?.play()
    }


    //영상 제작자 프로필 열기
    @JvmStatic
    @BindingAdapter("getcontext","getuid")
    fun openProfile(view: View,context : Context,uid: String) {
        view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(context, UserActivity::class.java)
                intent.putExtra("Uid", uid)
                context.startActivity(intent)
            }
        })
    }






}