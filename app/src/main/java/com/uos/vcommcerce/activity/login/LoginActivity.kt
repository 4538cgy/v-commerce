package com.uos.vcommcerce.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.R
import com.uos.vcommcerce.activity.main.MainActivity
import com.uos.vcommcerce.activity.signup.SignUpActivity
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityLoginBinding
import com.uos.vcommcerce.util.EventObserver
import com.uos.vcommcerce.MyApplication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    layoutId = R.layout.activity_login
) {

    companion object {
        private const val GOOGLE_LOGIN_CODE = 9001
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    var callbackManager: CallbackManager? = null

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        Glide.with(this).load(R.drawable.gradient_login_background)
            .into(binding.activityLoginImageviewBackgroundGif)

        callbackManager = CallbackManager.Factory.create()

        viewModel.googleLogin.observe(this, EventObserver {
            viewModel.createCustomToken("123")
            //googleLogin()
        })

        viewModel.faceBookLogin.observe(this, EventObserver {
            facebookLogin()
        })

        viewModel.localLogin.observe(this, EventObserver {
            startActivity(Intent(this, LoginWithEmail::class.java))
        })
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth.currentUser)
    }

    private fun googleLogin() {
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_LOGIN_CODE)
    }

    private fun facebookLogin() {
        LoginManager.getInstance().let { manager ->
            manager.logInWithReadPermissions(this, listOf("public_profile", "email"))
            manager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    //second step
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }
            })
        }
    }

    fun handleFacebookAccessToken(token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                moveMainPage(task.result?.user)
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_LOGIN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //login
                moveMainPage(task.result?.user)
            } else {
                //show the error message
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            firestore.collection("userInfo").document("userData").collection("accountInfo")
                .document(auth.currentUser?.uid!!)
                .addSnapshotListener { documentSnapshot, exception ->

                    if (documentSnapshot != null) {
                        if (documentSnapshot!!.exists()) {
                            MyApplication.prefs.setString("userInfo", "yes")
                            Toast.makeText(
                                this,
                                "회원정보가 존재합니다. \n 메인 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            MyApplication.prefs.setString("userInfo", "no")
                            Toast.makeText(
                                this,
                                "회원정보가 존재하지 않습니다. \n 회원 정보 추가 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (MyApplication.prefs.getString("userInfo", "no") == "yes") {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, SignUpActivity::class.java))
                        }
                        finish()
                    }
                }
        }
    }
}