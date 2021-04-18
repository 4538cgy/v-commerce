package com.uos.vcommcerce.activity.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.productinformation.ProductInformationActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.ActivityUserViewBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.datamodel.UserProfileModel
import com.uos.vcommcerce.datamodel.UserVideoData
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity

private const val FLAG_PERM_CAMERA = 98
private const val FLAG_PERM_STORAGE = 99
private const val FLAG_REQ_CAMERA = 101
private const val FLAG_REQ_GALLERY = 102
private const val FLAG_FIX_RESULT = 103

class UserActivity : BaseActivity<ActivityUserViewBinding>(layoutId = R.layout.activity_user_view) {

    private val userProfileModel: UserProfileModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.useractivity = this
        binding.userprofile = userProfileModel

        userProfileModel.setUserUid(intent.getStringExtra("Uid"))

        //기본 그리드 뷰 실행
        if (userProfileModel.firebaseAuth?.currentUser != null) {   //로그인 체크 이거 맞나?
            binding.followBtn.visibility = View.VISIBLE
            binding.messageBtn.visibility = View.VISIBLE
            Log.d("currentUser1 : ", userProfileModel.firebaseAuth?.currentUser!!.uid.toString())
        } else {
            binding.followBtn.visibility = View.INVISIBLE
            binding.messageBtn.visibility = View.INVISIBLE
            Log.d("currentUser2 : ", userProfileModel.firebaseAuth?.currentUser.toString())
        }


        if (intent.getStringExtra("history") == "history") {
            supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, HistoryFragment()).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, VideoGridFragment()).commit()
        }

    }

    //비디오 탭 클릭 이벤트
    fun videoTabClickEvent(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, VideoGridFragment()).commit()
    }

    //히스토리 탭 클릭 이벤트
    fun historyTabClickEvent(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.recyclerViewBox, HistoryFragment()).commit()
    }

    //프로필 수정 페이지로 이동
    fun moveFixUserActivity(view: View) {
        var intent = Intent(binding.root.context, FixUserActivity::class.java)
        intent.apply {
            putExtra("Name", userProfileModel.userInfo.value!!.userNickName)
            putExtra("Introduction", userProfileModel.userInfo.value!!.introduce)
            putExtra("Uri", userProfileModel.userInfo.value!!.profileImg.toString())
        }
        startActivityForResult(intent, FLAG_FIX_RESULT)
    }

    //결과 받아오는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Log.d("카메라","req=$requestCode, result = $resultCode, data = $data")
        if (resultCode == Activity.RESULT_OK) {
            Log.d("resultCode : ", "Activity.RESULT_OK")

            if (data?.getStringExtra("Name") != null) {
                userProfileModel.userInfo.value!!.userNickName = (data?.getStringExtra("Name"))
                Log.d("NickName 값: ", userProfileModel.userInfo.value!!.userNickName)
            } else {
                userProfileModel.userInfo.value!!.userNickName= ""
            }

            if (data?.getStringExtra("Introduction") != null) {
                userProfileModel.userInfo.value!!.introduce = (data?.getStringExtra("Introduction"))
                Log.d("Introduction 값: ", userProfileModel.userInfo.value!!.introduce)
            } else {
                userProfileModel.userInfo.value!!.introduce =""
            }

            Log.d("Uri 값: ", data?.getStringExtra("Uri"))

            if (data?.getStringExtra("Uri") != "null") {
                userProfileModel.userInfo.value!!.profileImg = (data?.getStringExtra("Uri"))
                binding.profileImg.setImageURI( Uri.parse(userProfileModel.userInfo.value!!.profileImg) )
                Log.d("Uri 값: ", userProfileModel.userInfo.value!!.profileImg)

            } else {
                userProfileModel.userInfo.value!!.profileImg = ""
                binding.profileImg.setImageResource(R.mipmap.ic_launcher)
                Log.d("Uri 값: ", "설정안됨")
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("resultCode : ", "Activity.RESULT_CANCELED")
        }
    }

    fun moveProductInfomation(view: View) {
        var intent = Intent(binding.root.context, ProductInformationActivity::class.java)
        startActivity(intent)
    }

}


class RecyclerProfileVideoAdapter(var context: Context, layoutId : Int, itemlist : ArrayList<UserVideoData> )
    : BaseRecyclerAdapter<UserVideoData, VideoGridItemBinding>(layoutId,itemlist){
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.adapter = this
    }

    fun itemClick(videoData: UserVideoData){
        val vedioIntent = Intent( context, TestExoplayerActivity::class.java)
        vedioIntent.putExtra("img", videoData.Img)
        vedioIntent.putExtra("url", videoData.Url)
        context.startActivity(vedioIntent)
    }
}




