package com.uos.vcommcerce.activity.login

import androidx.lifecycle.viewModelScope
import com.uos.vcommcerce.base.BaseViewModel
import com.uos.vcommcerce.data.repository.MemberRepository
import com.uos.vcommcerce.util.livedata.EventLiveData
import com.uos.vcommcerce.util.livedata.MutableEventLiveData
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val memberRepository: MemberRepository) : BaseViewModel() {

    private val _faceBookLogin: MutableEventLiveData<Unit> = MutableEventLiveData()
    var faceBookLogin = _faceBookLogin

    private val _googleLogin: MutableEventLiveData<Unit> = MutableEventLiveData()
    var googleLogin: EventLiveData<Unit> = _googleLogin

    private val _kakaoLogin: MutableEventLiveData<Unit> = MutableEventLiveData()
    var kakaoLogin: EventLiveData<Unit> = _kakaoLogin

    private val _localLogin: MutableEventLiveData<Unit> = MutableEventLiveData()
    var localLogin: EventLiveData<Unit> = _localLogin

    fun clickKakaoLogin() {
        _kakaoLogin.event = Unit
    }

    fun clickGoogleLogin() {
        _googleLogin.event = Unit
    }

    fun clickFacebookLogin() {
        _faceBookLogin.event = Unit
    }

    fun clickLocalLogin() {
        _localLogin.event = Unit
    }

    fun createCustomToken(uniqueId: String) {
        viewModelScope.launch {
            memberRepository.createCustomToken(uniqueId)
                .catch { throttle ->
                    Timber.e(throttle)
                }
                .collect {
                    Timber.tag("test").e(it.toString())
                }
        }
    }
}