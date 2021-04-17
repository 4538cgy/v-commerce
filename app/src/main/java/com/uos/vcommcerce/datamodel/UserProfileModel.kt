package com.uos.vcommcerce.datamodel

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.uos.vcommcerce.firebase.repository.FirebaseContentRepository
import com.uos.vcommcerce.firebase.repository.FirebaseUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserProfileModel() : ViewModel() {


    //파이어 베이스 저장소
    val repository = FirebaseUserRepository()

    //본인 확인용
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    //프로필 유저 데이터
    var userInfo : MutableLiveData<UserDTO> = MutableLiveData<UserDTO>()

    //파베에서 가져온 데이터 삽입
    fun setUserUid(userUid : String ){
        Log.d("찾는사람!" ,""+userUid)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserData(userUid).collect {
                userInfo.postValue(it)
            }
        }
    }
}

