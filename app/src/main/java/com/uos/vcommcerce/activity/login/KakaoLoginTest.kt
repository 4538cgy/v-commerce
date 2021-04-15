package com.uos.vcommcerce.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityKakaoLoginTestBinding
import com.uos.vcommcerce.datamodel.KakaoTestDTO
import com.uos.vcommcerce.http.dto.HttpResponseDTO2
import com.uos.vcommcerce.http.release.RestApiRelease
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoLoginTest : BaseActivity<ActivityKakaoLoginTestBinding>(layoutId = R.layout.activity_kakao_login_test) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.kakaologin = this

        KakaoSdk.init(this,"a72718d671fca3ab14ecdc6c7f88eb3d")
    }

    fun kakaoLogin(view : View){
        UserApiClient.instance.loginWithKakaoAccount(binding.root.context) {
                token, error ->
            if (error != null){
                Toast.makeText(binding.root.context, "로그인 실패 $error", Toast.LENGTH_LONG).show()
            }else if (token != null){
                Toast.makeText(binding.root.context, "로그인 성공 ${token.accessToken}", Toast.LENGTH_LONG ).show()
            }
        }
    }

    fun kakaoId(view : View){
        UserApiClient.instance.me { user, error ->
            if (error != null){
                println("에러")
            }else if (user!=null){
                println("유저 아이디이이이이잌 ${user.id}")
            }
        }
    }

    fun kakaotalkLogin(view : View){
        UserApiClient.instance.loginWithKakaoTalk(binding.root.context) {
                token , error ->
            if (error != null){
                Toast.makeText(binding.root.context,"로그인 실패 $error", Toast.LENGTH_LONG).show()
            }else if (token != null){
                Toast.makeText(binding.root.context,"로그인 성공 ${token.accessToken}" ,Toast.LENGTH_LONG).show()
                println("카카오톡 로그인 성공 토크으으으으은 ${token.accessToken}" )

                var kakaoModel = KakaoTestDTO(token.accessToken,"kakao")

                RestApiRelease().test(kakaoModel).enqueue(object : Callback<HttpResponseDTO2.customTokenDTO>{


                    override fun onFailure(
                        call: Call<HttpResponseDTO2.customTokenDTO>,
                        t: Throwable
                    ) {
                        println("우아아아아아앜 토큰 받아오기 실패")
                    }

                    override fun onResponse(
                        call: Call<HttpResponseDTO2.customTokenDTO>,
                        response: Response<HttpResponseDTO2.customTokenDTO>
                    ) {

                        println("우아아아아앜 토큰 받아오기 성공")
                        println("-----------------------------" )
                        println("body = ${response.body()}")
                        println("token = ${response.body()?.token}")
                        println("status = ${response.body()?.status}")
                        println("detail = ${response.body()?.detail}")
                        println("-----------------------------")

                        FirebaseAuth.getInstance().signInWithCustomToken(response.body()?.token!!)
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    println("-----------------------------")
                                    println("signInWithCustomToken:success")
                                    println("${FirebaseAuth.getInstance().currentUser?.uid}")
                                    println("-----------------------------")
                                }else{
                                    println("뭐야 시벌 안되잖아?")
                                }
                            }
                    }
                })
            }
        }
    }



}

