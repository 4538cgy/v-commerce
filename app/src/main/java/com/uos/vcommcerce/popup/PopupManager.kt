package com.uos.vcommcerce.popup

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.PopupDatePickerBinding
import com.uos.vcommcerce.databinding.PopupNotiBinding
import com.uos.vcommcerce.databinding.PopupPictureOptionBinding
import com.uos.vcommcerce.util.Delegate
import java.util.*

/** Popup 관리 Manager로 show 함수 위에 팝업 생성 함수 작성
 사용 방법
 1. xml을 만든다. (참고는 popup_picture_option, or popup_noti)
 2. binding 한다.  ex) val binding: PopupPictureOptionBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.popup_picture_option, null, false)
 3. dialog를 생성한다. ex) dialog = Dialog(activity!!, R.style.CustomTheme_CustomPopup)
 4. 생성한 dialog 가 Null이 아닐 경우 그 dialog 관련하여 액션을 만든다.
 5. 함수 생성이 완료되었다면, 호출할 class에서 PopupManager(activity).함수명.show(boolean)을 사용하면 팝업이 나타난다.
 6. DialogClickListener 나 DialogClickCallBackStringListener 를 통하여 callback 처리를 할 수 있다.
 참고 pictureOptionPopup / dataPickerPopup / notiPopup
 dismiss() 함수는 dialog를 닫는 함수이다.
 setCancelable 은 true로 하면 뒤로가기 키와 배경 터치를 통하여 팝업을 닫을 수 있고, false를 하면 해당 이벤트로 취소 할 수 없습니다.
 */

class PopupManager(activity: Activity) {
    //버튼 액션 Callback 인터페이스
    interface DialogClickListener {
        fun okBtn()
        fun cancelBtn()
    }
    // String 반환 callback 인터페이스
    interface DialogClickCallBackStringListener {
        fun okBtn(result: String)
    }

    init {
        dismiss()
    }

    companion object {
        var dialog: Dialog? = null
    }

    protected var activity: Activity? = activity

    protected var autoDismiss = true
    protected var callback: Delegate.Action? = null  // setCancelable 을 true 할 경우 배경 터치나 뒤로가기로 팝업 닫았을 경우에 대한 처리 callback

    fun dismiss() {
        dialog = try {
            if (null != dialog && dialog!!.isShowing) dialog!!.dismiss()
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun getDialog(): Dialog? {
        return dialog
    }

    // 사진 촬영, 선택  팝업
    fun pictureOptionPopup(listener: DialogClickListener?): PopupManager {
        val binding: PopupPictureOptionBinding = DataBindingUtil
            .inflate(LayoutInflater.from(activity), R.layout.popup_picture_option, null, false)

        dialog = Dialog(activity!!, R.style.CustomTheme_CustomPopup)
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(binding.root)
            // 사진 촬영
            binding.popupPictureOptionTakePictureLayout.setOnClickListener {
                if (listener == null) return@setOnClickListener
                listener.okBtn()
            }
            // 사진 선택
            binding.popupPictureOptionSelectPictureLayout.setOnClickListener {
                if (listener == null) return@setOnClickListener
                listener.cancelBtn()
            }
            //확인 버튼
            binding.popupPictureOptionConfirmBtn.setOnClickListener(View.OnClickListener {
                if (autoDismiss) {
                    dismiss()
                }
            })
        }
        return this
    }
    // 생일 선택 Picker Popup
    fun dataPickerPopup(listener: DialogClickCallBackStringListener?): PopupManager {
        val binding: PopupDatePickerBinding = DataBindingUtil
            .inflate(LayoutInflater.from(activity), R.layout.popup_date_picker, null, false)

        dialog = Dialog(activity!!, R.style.CustomTheme_CustomPopup)
        dialog?.let {

            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(binding.root)
            var result: String = ""
            val currentDate = Calendar.getInstance()
            binding.popupDatePickerDatepicker.init(
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            ) { view, year, monthOfYear, dayOfMonth ->
                result = "$year.${monthOfYear+1}.$dayOfMonth" }
            binding.popupDatePickerConfirmBtn.setOnClickListener {
                if (autoDismiss) {
                    dismiss()
                }
                if (listener == null) return@setOnClickListener
                if(result == ""){
                    result = "${currentDate.get(Calendar.YEAR)}.${currentDate.get(Calendar.MONTH)+1}.${
                        currentDate.get(Calendar.DAY_OF_MONTH)}"
                }
                listener.okBtn(result)
            }
            binding.popupDatePickerCancelBtn.setOnClickListener(View.OnClickListener {
                if (autoDismiss) {
                    dismiss()
                }
            })
        }
        return this
    }
    // 일반 메세지 확인 용 팝업 (단순 메세지만 보여주고 확인 누를 시 사라지는 팝업)
    fun notiPopup(msg : String) : PopupManager{
        val binding : PopupNotiBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.popup_noti, null, false)
        dialog = Dialog(activity!!, R.style.CustomTheme_CustomPopup)
        dialog?.let{
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(binding.root)
            binding.notimesage = msg
            binding.popupNotiConfirmBtn.setOnClickListener {
                if (autoDismiss) {
                    dismiss()
                }
            }
        }
        return this
    }
    // 다이얼로그 팝업 노출 함수
    fun show(isCancelable: Boolean) {
        if (dialog == null) {
            return
        }
        if (activity == null || activity!!.isFinishing) return
        dialog?.let {
            it.setOnDismissListener(DialogInterface.OnDismissListener { dialog ->
                if (dialog === it) {
                    if (callback != null) {
                        callback!!.run()
                    }
                }
            })
            dialog!!.show()
            dialog!!.setCancelable(isCancelable)
        }
    }
}