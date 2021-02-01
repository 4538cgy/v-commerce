package com.uos.vcommcerce.activity.regist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityRegistSellerBinding
import com.uos.vcommcerce.model.SellerDTO
import com.uos.vcommcerce.util.Config

/**
 *  2021.1.23 작성자 박정우
 *  판매자 등록 기본 페이지.
 *  문서1 파일 업로드 부분은 미구현
 */
class RegistSellerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistSellerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 데이터 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_seller)
        binding.activityregistseller = this@RegistSellerActivity

        // 우측 상단의 화면 닫는 X버튼 클릭
        binding.activityRegistSellerImageviewClose.setOnClickListener { finish() }
        // 개인 사용자 클릭
        binding.activityRegistSellerLayoutIndividualMember.setOnClickListener {
            binding.activityRegistSellerLayoutIndividualMember.isSelected = true
            binding.activityRegistSellerLayoutCorporateMember.isSelected = false
            binding.activityRegistSellerLayoutCorporateInfo.visibility = View.GONE
            checNextBtnEnable()
        }
        //기업 사용자 클릭
        binding.activityRegistSellerLayoutCorporateMember.setOnClickListener {
            binding.activityRegistSellerLayoutIndividualMember.isSelected = false
            binding.activityRegistSellerLayoutCorporateMember.isSelected = true
            binding.activityRegistSellerLayoutCorporateInfo.visibility = View.VISIBLE
            checNextBtnEnable()

        }

        // 이용약관 1,2,3,4 체크
        binding.activityRegistSellerLayoutTermsOfService1.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService2.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService3.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService4.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)

        // Next 버튼클릭
        binding.activityRegistSellerButtonNext.setOnClickListener {
            val intent = Intent(this, RegistSellerInfoActivity::class.java)
            val sellerDTO = SellerDTO().apply {
                if(binding.activityRegistSellerLayoutCorporateMember.isSelected) {
                    isCorporate = true
                }else if(binding.activityRegistSellerLayoutIndividualMember.isSelected){
                    isPersonal = true
                }
                corporateNum = binding.activityRegistSellerEdittextCompanyRegistrationNumber.text.toString()
                corporateRepresentativeName = binding.activityRegistSellerEdittextRepresentativeName.text.toString()

            }

            intent.putExtra(Config.Seller, sellerDTO)
            startActivity(intent)
        }
    }
    // 이용약관의 체크박스 클릭 시 동작
    private val checkBoxChangeListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            checNextBtnEnable()
        }

    }
    // 다음으로 진행 할 수 있는지 체크 이용약관 모두 동의하고, 기업인지 개인인지 체크해야함크.
    // 다음으로 넘어갈 수 있다면 Next 버튼이 활성화가 된다.
    private fun checNextBtnEnable() {

        if (binding.activityRegistSellerLayoutIndividualMember.isSelected) {
            if (checkBoxAllChecked()) {
                binding.activityRegistSellerButtonNext.apply {
                    isClickable = true
                    setBackgroundResource(R.drawable.button_background_round_black_16dp)
                }
            } else {
                binding.activityRegistSellerButtonNext.apply {
                    isClickable = false
                    setBackgroundResource(R.drawable.button_background_round_c4c4c4_16dp)
                }
            }
        } else if (binding.activityRegistSellerLayoutCorporateMember.isSelected) {
            if (checkBoxAllChecked()) {
                binding.activityRegistSellerButtonNext.apply {
                    isClickable = true
                    setBackgroundResource(R.drawable.button_background_round_black_16dp)
                }
            } else {
                binding.activityRegistSellerButtonNext.apply {
                    isClickable = false
                    setBackgroundResource(R.drawable.button_background_round_c4c4c4_16dp)
                }
            }
        } else {
            binding.activityRegistSellerButtonNext.apply {
                isClickable = false
                setBackgroundResource(R.drawable.button_background_round_c4c4c4_16dp)
            }
        }

    }

    // 이용약관 1,2,3,4 각 체크되었는지 체
    private fun checkBoxAllChecked(): Boolean {
        return binding.activityRegistSellerLayoutTermsOfService1.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService2.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService3.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService4.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked
    }
}