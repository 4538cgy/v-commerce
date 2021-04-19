package com.uos.vcommcerce.activity.regist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.main.MainActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityRegistSellerInfoBinding
import com.uos.vcommcerce.datamodel.SellerDTO
import com.uos.vcommcerce.util.Config
import com.uos.vcommcerce.MyApplication

/**
 *  2021.1.23 작성자 박정우
 *  판매자 등록 상세 정보 작성 화면
 *  임시 저장 기능 개발 필요
 */
class RegistSellerInfoActivity : BaseActivity<ActivityRegistSellerInfoBinding>(
    layoutId = R.layout.activity_regist_seller_info
) {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    lateinit var sellerInfo: SellerDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            activityregistsellerinfo = this@RegistSellerInfoActivity
            activityRegistSellerImageviewClose.setOnClickListener { finish() }
            //각각 EditText Wather 등록 (모두 입력되어야 등록 하도록 하기 위해)
            activityRegistSellerInfoEdittextChannelAddress.addTextChangedListener(textWatcher)
            activityRegistSellerInfoEdittextChannelIntroduce.addTextChangedListener(textWatcher)
            activityRegistSellerInfoEdittextChannelName.addTextChangedListener(textWatcher)
            activityRegistSellerInfoEdittextBankAccount.addTextChangedListener(textWatcher)
            activityRegistSellerButtonNext.setOnClickListener {
                // 각 Edittext 값을 firebase에 전달할 데이터에 적용
                sellerInfo.bankAccount = activityRegistSellerInfoEdittextBankAccount.text.toString()
                sellerInfo.channelExplain =
                    activityRegistSellerInfoEdittextChannelIntroduce.text.toString()
                sellerInfo.channelName = activityRegistSellerInfoEdittextChannelName.text.toString()
                sellerInfo.channelUrl =
                    activityRegistSellerInfoEdittextChannelAddress.text.toString()

                // firebase에 등록
                firestore.collection(Config.userInfo).document("userData")
                    .collection(Config.sellerInfo).document(auth.currentUser?.uid.toString())
                    .set(sellerInfo)
                    .addOnSuccessListener {
                        startActivity(
                            Intent(
                                binding.root.context,
                                MainActivity::class.java
                            )
                        )
                        Toast.makeText(binding.root.context, " 판매자 등록 완료 ", Toast.LENGTH_SHORT)
                            .show()
                        MyApplication.prefs.setString(Config.sellerInfo, "yes")
                        finish()
                    }

            }
        }
        // 이전 화면에서 등록한 정보 적용
        sellerInfo = intent.getSerializableExtra(Config.Seller) as SellerDTO

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 입력 시 모든 Edittext가 입력되면 버튼 활성화 하도록
            with(binding) {
                if (activityRegistSellerInfoEdittextChannelAddress.text.isNotEmpty() &&
                    activityRegistSellerInfoEdittextChannelIntroduce.text.isNotEmpty() &&
                    activityRegistSellerInfoEdittextChannelName.text.isNotEmpty() &&
                    activityRegistSellerInfoEdittextBankAccount.text.isNotEmpty()
                ) {
                    activityRegistSellerButtonNext.apply {
                        isClickable = true
                        setBackgroundResource(R.drawable.button_background_round_black_16dp)
                    }
                } else {
                    activityRegistSellerButtonNext.apply {
                        isClickable = false
                        setBackgroundResource(R.drawable.button_background_round_c4c4c4_16dp)
                    }
                }
            }

        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
}