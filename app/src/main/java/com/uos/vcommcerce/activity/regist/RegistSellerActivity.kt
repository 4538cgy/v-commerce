package com.uos.vcommcerce.activity.regist

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.login.LoginActivity
import com.uos.vcommcerce.databinding.ActivityRegistSellerBinding

class RegistSellerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistSellerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_seller)
        binding.activityregistseller = this@RegistSellerActivity

        binding.activityRegistSellerImageviewClose.setOnClickListener { finish() }
        binding.activityRegistSellerLayoutIndividualMember.setOnClickListener {
            binding.activityRegistSellerLayoutIndividualMember.isSelected = true
            binding.activityRegistSellerLayoutCorporateMember.isSelected = false
            binding.activityRegistSellerLayoutCorporateInfo.visibility = View.GONE
            checNextBtnEnable()
        }
        binding.activityRegistSellerLayoutCorporateMember.setOnClickListener {
            binding.activityRegistSellerLayoutIndividualMember.isSelected = false
            binding.activityRegistSellerLayoutCorporateMember.isSelected = true
            binding.activityRegistSellerLayoutCorporateInfo.visibility = View.VISIBLE
            checNextBtnEnable()

        }

        binding.activityRegistSellerLayoutTermsOfService1.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService2.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService3.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerLayoutTermsOfService4.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox)
            .setOnCheckedChangeListener(checkBoxChangeListener)
        binding.activityRegistSellerButtonNext.setOnClickListener {

            startActivity(Intent(this, RegistSellerInfoActivity::class.java))
        }
    }

    private val checkBoxChangeListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            checNextBtnEnable()
        }

    }

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

    private fun checkBoxAllChecked(): Boolean {
        return binding.activityRegistSellerLayoutTermsOfService1.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService2.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService3.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked &&
                binding.activityRegistSellerLayoutTermsOfService4.findViewById<CheckBox>(R.id.item_terms_of_service_checkbox).isChecked
    }
}