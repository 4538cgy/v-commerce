package com.uos.vcommcerce.activity.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
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
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.activity.main.MainActivity
import com.uos.vcommcerce.activity.signup.SignUpActivity
import com.uos.vcommcerce.databinding.ActivityLoginBinding
import com.uos.vcommcerce.util.SharedData
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    var callbackManager: CallbackManager? = null
    var firestore = FirebaseFirestore.getInstance()

    lateinit var binding : ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.activitylogin = this@LoginActivity

        auth = FirebaseAuth.getInstance()

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        Glide.with(this).load(R.drawable.gradient_login_background).into(binding.activityLoginImageviewBackgroundGif)

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()

        //버튼 리스너
        binding.activityLoginButtonLoginGoogle.setOnClickListener {
            //구글 로그인
            googleLogin()
        }
        binding.activityLoginButtonLoginFacebook.setOnClickListener {
            //페이스북 로그인
            facebookLogin()
        }
        binding.activityLoginTextviewLoginwithemail.setOnClickListener {
            //이메일 로그인
            startActivity(Intent(this,LoginWithEmail::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
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

    fun handleFacebookAccessToken(token: AccessToken?) {

        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                //third step
                //login
                moveMainPage(task.result?.user)
            } else {
                //show the error message
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if (result.isSuccess) {
                    var account = result.signInAccount
                    //second step
                    firebaseAuthWithGoogle(account)
                }
            }
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //login
                moveMainPage(task.result?.user)
            } else {
                //show the error message
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {


            firestore.collection("userInfo").document("userData").collection("accountInfo")
                .document(auth?.currentUser?.uid!!)
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

                    if (documentSnapshot != null) {
                        if (documentSnapshot!!.exists()) {
                            SharedData.prefs.setString("userInfo", "yes")
                            Toast.makeText(this,"회원정보가 존재합니다. \n 메인 페이지로 이동합니다.",Toast.LENGTH_SHORT).show()
                            
                        } else {
                            SharedData.prefs.setString("userInfo", "no")
                            Toast.makeText(this,"회원정보가 존재하지 않습니다. \n 회원 정보 추가 페이지로 이동합니다.",Toast.LENGTH_SHORT).show()
                        }

                        if (SharedData.prefs.getString("userInfo", "no").equals("yes")) {
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