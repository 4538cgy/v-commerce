package com.uos.vcommcerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.activity.login.LoginActivity
import com.uos.vcommcerce.activity.oder.OrderActivity
import com.uos.vcommcerce.activity.signup.WelcomeActivity
import com.uos.vcommcerce.activity.videoupload.VideoSelectActivity
import com.uos.vcommcerce.activity.videoupload.VideoUploadActivity
import com.uos.vcommcerce.http.RestApi
import com.uos.vcommcerce.model.HttpResponseDTO
import com.uos.vcommcerce.model.SettingDTO
import com.uos.vcommcerce.testpackagedeletesoon.ShowMyUserInfoActivity
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import com.uos.vcommcerce.util.SharedData
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.item_setting.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SettingActivity : AppCompatActivity() {

    var gac: GoogleApiClient? = null
    var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        activity_setting_recycler?.adapter = SettingActivityRecyclerViewAdapter()
        activity_setting_recycler?.layoutManager = LinearLayoutManager(this)

        context = this.applicationContext

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        gac = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()


    }

    inner class SettingActivityRecyclerViewAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var settingDTO: ArrayList<SettingDTO> = arrayListOf()

        init {
            settingDTO.add(SettingDTO("로그인 상태" + FirebaseAuth.getInstance().currentUser.toString()))
            settingDTO.add(SettingDTO("메인 화면 보기"))
            settingDTO.add(SettingDTO("로그아웃"))
            settingDTO.add(SettingDTO("비디오 화면 보기"))
            settingDTO.add(SettingDTO("그리드 화면 보기"))
            settingDTO.add(SettingDTO("RESTFULL TEST"))
            settingDTO.add(SettingDTO("RESTFULL TEST POST"))
            settingDTO.add(SettingDTO("비디오 리스트 보기"))
            settingDTO.add(SettingDTO("비디오 업로드 하기"))
            settingDTO.add(SettingDTO("회원 가입 or 로그인 하기"))
            settingDTO.add(SettingDTO("저장된 정보 보기[로그인 상태에서만 가능]"))
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun getItemCount(): Int {
            return settingDTO.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            var view = holder.itemView
            view.item_setting_textview_title.text = settingDTO[position].title

            view.item_setting_textview_title.setOnClickListener {

                when (settingDTO[position].title) {

                    "메인 화면 보기" -> {
                        pageChange("Main")
                    }

                    "로그아웃" -> {
                        if (FirebaseAuth.getInstance().currentUser == null) {
                            Toast.makeText(context, "이미 로그아웃 되어있습니다.", Toast.LENGTH_LONG).show()
                        } else {
                            signOut()

                        }
                    }

                    "비디오 화면 보기" -> {
                        pageChange("Video")
                    }

                    "그리드 화면 보기" -> {
                        pageChange("Grid")
                    }

                    "비디오 리스트 보기" -> {
                        pageChange("VideoList")
                    }
                    "비디오 업로드 하기" -> {
                        pageChange("VideoUpload")
                    }

                    "회원 가입 or 로그인 하기" -> {
                        pageChange("SignUp")
                    }

                    "저장된 정보 보기[로그인 상태에서만 가능]" -> {
                        pageChange("stateshow")
                    }
                    "RESTFULL TEST" -> {
                        //상세 정보 조회
                        RestApi().getDetailApi("thisIsSamplePid")
                            .enqueue(object : Callback<HttpResponseDTO.DetailDTO> {
                                override fun onResponse(
                                    call: Call<HttpResponseDTO.DetailDTO>,
                                    response: Response<HttpResponseDTO.DetailDTO>
                                ) {

                                    Log.d("Retrofit 세부 검색", "response: ${response.body()}")
                                }

                                override fun onFailure(
                                    call: Call<HttpResponseDTO.DetailDTO>,
                                    t: Throwable
                                ) {
                                    Log.e("Retrofit 세부검색 실패", "response: ${t.toString()}")
                                }
                                /*

                            //전체 리스트 조회
                            RestApi().getSearchListApi("22bbccdd").enqueue(object :
                                Callback<HttpResponseDTO.SearchAllListDTO> {
                                override fun onResponse(
                                    call: Call<HttpResponseDTO.SearchAllListDTO>,
                                    response: Response<HttpResponseDTO.SearchAllListDTO>
                                ) {
                                    Log.d("Retrofit", "response: ${response.body()}")
                                }

                                override fun onFailure(call: Call<HttpResponseDTO.SearchAllListDTO>, t: Throwable) {

                                    Log.e("Retrofit", "response: ${t.toString()}")
                                }

                            })

                             */


                            })
                    }
                    "RESTFULL TEST POST" -> {
                        // file 생성 부분을 고쳐줘야함. 영상을 찍고 그 영상을 선택하여 올려 줄 수 있도록
                        val filename = "myfile.txt"
                        val directory = applicationContext.filesDir
                        val file = File(directory, filename)
                        if (!file.exists()) {
                            val fileContents = "Hello world!"
                            applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
                                it.write(fileContents.toByteArray())
                            }
                        }

                        val category = ArrayList<String>()
                        category.add("건강기능식품")
                        category.add("건강식품")
                        val uploadContentDTO = HttpResponseDTO.UploadContentDTO(
                            token = "abmnsda23349asdm1239c72",
                            uid = "29df898eqr738sdf91g",
                            title = "100년 묵은 홍삼",
                            body = "여러분 안녕하세요~ \n오늘은 잇님들에게 100년 묵은 홍삼을 소개시켜드리려고 해요~~~\n(X같은 문 이모티콘)",
                            category = category
                        )
                        RestApi().uploadApi(uploadContentDTO, file).enqueue(object :
                            Callback<Void> {
                            override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                            ) {
                                Log.d("Retrofit", "response: ${response.body()}")
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {

                                Log.e("Retrofit", "response: ${t.toString()}")
                            }

                        })
                    }
                }

            }


        }


    }

    fun pageChange(type: String) {


        when (type) {
            "Login" -> {

                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }

            "Main" -> {

                startActivity(Intent(this, MainActivity::class.java))

            }

            "Video" -> {

                startActivity(Intent(this, TestExoplayerActivity::class.java))

            }

            "Grid" -> {
                startActivity(Intent(this, UserActivity::class.java))
            }
            "Order" -> {
                startActivity(Intent(this, OrderActivity::class.java))
            }

            "VideoList" -> {
                startActivity(Intent(this, VideoSelectActivity::class.java))
            }

            "VideoUpload" -> {
                startActivity(Intent(this, VideoUploadActivity::class.java))
            }

            "SignUp" -> {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }

            "stateshow" -> {
                startActivity(Intent(this, ShowMyUserInfoActivity::class.java))
            }


        }
    }

    fun signOut() {
        gac?.connect()



        gac?.registerConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
            override fun onConnected(bundle: Bundle?) {
                FirebaseAuth.getInstance().signOut()
                if (gac!!.isConnected()) {
                    Auth.GoogleSignInApi.signOut(gac).setResultCallback { status ->
                        if (status.isSuccess) {
                            Log.v("알림", "로그아웃 성공")
                            if (SharedData.prefs.getString("userInfo", "no") != null) {
                                SharedData.prefs.setString("userInfo", "no")
                            }
                            activity_setting_recycler?.adapter =
                                SettingActivityRecyclerViewAdapter()
                            Toast.makeText(this@SettingActivity, "로그아웃 성공", Toast.LENGTH_SHORT)
                                .show()


                            setResult(1)
                        } else {
                            setResult(0)
                        }
                    }
                }
            }

            override fun onConnectionSuspended(i: Int) {
                Log.v("알림", "Google API Client Connection Suspended")
                setResult(-1)
            }
        })


    }

    override fun onResume() {
        super.onResume()
        activity_setting_recycler?.adapter = SettingActivityRecyclerViewAdapter()
    }
}