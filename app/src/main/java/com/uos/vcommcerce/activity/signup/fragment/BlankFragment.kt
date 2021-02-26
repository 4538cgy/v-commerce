package com.uos.vcommcerce.activity.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.FragmentBlankBinding
import com.uos.vcommcerce.datamodel.UserDTO

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding : FragmentBlankBinding
    var users = UserDTO()
    var string : String ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_blank,container,false)

        users.uid = "hello world!"



        binding.blank1Test.setOnClickListener {
            string = binding.fragmentBlankEdittext.text.toString()
            binding.blank1Test.text = string
        }

        print("blank 1 = ${users.uid}")
        return binding.root
    }


}