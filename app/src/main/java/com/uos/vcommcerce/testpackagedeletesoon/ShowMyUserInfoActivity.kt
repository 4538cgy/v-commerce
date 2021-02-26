package com.uos.vcommcerce.testpackagedeletesoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityShowMyUserInfoBinding
import com.uos.vcommcerce.datamodel.UserDTO

class ShowMyUserInfoActivity : AppCompatActivity() {

    lateinit var binding:ActivityShowMyUserInfoBinding
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var users = UserDTO()

    init {

        firestore.collection("userInfo").document("userData").collection("accountInfo").document(auth.currentUser?.uid!!)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot != null){
                    if (documentSnapshot.exists()) {

                        users = documentSnapshot.toObject(UserDTO::class.java)!!

                        ViewChange()
                        Toast.makeText(binding.root.context, "회원정보 불러오기 성공", Toast.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    Toast.makeText(binding.root.context,"회원 정보 불러오기 실패",Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_my_user_info)
        binding.activityshowmyuserinfo = this@ShowMyUserInfoActivity

        binding.testTextviewUid.text = auth.currentUser?.uid.toString()
        binding.testTextviewProviderid.text = auth.currentUser?.providerId.toString()
        binding.testTextviewPhotoUrl.text = auth.currentUser?.photoUrl.toString()
        binding.testTextviewPhonenumber.text = auth.currentUser?.phoneNumber.toString()
        binding.testTextviewEmail.text = auth.currentUser?.email.toString()



    }

    fun ViewChange(){
        binding.testTextviewUiddb.text = users.uid
        binding.testTextviewAddress.text = users.address
        binding.testTextviewNickname.text = users.userNickName
        binding.testTextviewPhonenumberdb.text = users.phoneNumber
    }
}