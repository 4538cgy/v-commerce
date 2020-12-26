package com.uos.vcommcerce.activity.signup.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.databinding.FragmentAddUserInfoBinding
import com.uos.vcommcerce.model.SettingDTO
import com.uos.vcommcerce.model.UserDTO
import com.uos.vcommcerce.util.SharedData
import com.uos.vcommcerce.util.TimeUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddUserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddUserInfoFragment : Fragment() {

    lateinit var binding : FragmentAddUserInfoBinding
    var firestore = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_user_info,container,false)

        binding.fragmentAddUserInfoImagebuttonBack.setOnClickListener {
            activity?.finish()
        }

        binding.fragmentAddUserInfoButtonUpload.setOnClickListener {
            uploadUserInfo()
        }

        return binding.root
    }

    fun uploadUserInfo(){

        var users = UserDTO()
        users.uid = auth.currentUser?.uid.toString()
        users.address = binding.fragmentAddUserInfoEdittextAddress.text.toString()
        users.userNickName = binding.fragmentAddUserInfoEdittextNickname.text.toString()
        users.phoneNumber = binding.fragmentAddUserInfoEdittextPhonenumber.text.toString()
        users.serverTimestamp = System.currentTimeMillis()
        users.timestamp = TimeUtil().getTimeAll()

        firestore.collection("userInfo").document("userData").collection("accountInfo").document(auth.currentUser?.uid.toString()).set(users)
            .addOnSuccessListener {
                activity?.startActivity(Intent(binding.root.context,SettingActivity::class.java))
                Toast.makeText(binding.root.context," 회원 가입 완료 ",Toast.LENGTH_SHORT).show()
                SharedData.prefs.setString("userInfo","yes")
                activity?.finish()
            }


    }




}