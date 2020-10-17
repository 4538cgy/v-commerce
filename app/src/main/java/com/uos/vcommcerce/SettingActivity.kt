package com.uos.vcommcerce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.uos.vcommcerce.Http.RestApi
import com.uos.vcommcerce.Model.HttpResponseDTO
import com.uos.vcommcerce.Model.SettingDTO
import com.uos.vcommcerce.TestPackageDeleteSoon.TestExoplayerActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.item_setting.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity() {

    var gac : GoogleApiClient ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        activity_setting_recycler?.adapter = SettingActivityRecyclerViewAdapter()
        activity_setting_recycler?.layoutManager = LinearLayoutManager(this)


        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        gac = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()



    }

    inner class SettingActivityRecyclerViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        var settingDTO : ArrayList<SettingDTO> = arrayListOf()

        init {
            settingDTO.add(SettingDTO("메인 화면 보기"))
            settingDTO.add(SettingDTO("로그아웃"))
            settingDTO.add(SettingDTO("비디오 화면 보기"))
            settingDTO.add(SettingDTO("그리드 화면 보기"))
            settingDTO.add(SettingDTO("RESTFULL TEST"))
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        }

        override fun getItemCount(): Int {
            return  settingDTO.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            var view = holder.itemView
            view.item_setting_textview_title.text = settingDTO[position].title

            view.item_setting_textview_title.setOnClickListener {

                when(settingDTO[position].title){

                    "메인 화면 보기"->{
                        pageChange("Main")
                    }

                    "로그아웃"->{
                        signOut()
                    }

                    "비디오 화면 보기"->{
                        pageChange("Video")
                    }

                    "그리드 화면 보기" -> {
                        pageChange("Grid")
                    }
                    "RESTFULL TEST" -> {

                        //상세 정보 조회
                        RestApi().getDetailApi("thisIsSamplePid").enqueue(object : Callback<HttpResponseDTO.DetailDTO>{
                            override fun onResponse(
                                call: Call<HttpResponseDTO.DetailDTO>,
                                response: Response<HttpResponseDTO.DetailDTO>
                            ) {
                                
                                Log.d("Retrofit 세부 검색" , "response: ${response.body()}")
                            }

                            override fun onFailure(
                                call: Call<HttpResponseDTO.DetailDTO>,
                                t: Throwable
                            ) {
                                Log.e("Retrofit 세부검색 실패","response: ${t.toString()}")
                            }


                        })



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
                        

                    }
                }

            }


        }


    }

    fun pageChange(type : String){


        when(type){
            "Login" ->{

                startActivity(Intent(this,LoginActivity::class.java))

            }

            "Main" ->{

                startActivity(Intent(this,MainActivity::class.java))

            }

            "Video" ->{

                startActivity(Intent(this,TestExoplayerActivity::class.java))

            }

            "Grid" -> {
                startActivity(Intent(this,UserActivity::class.java))
            }


        }
    }

    fun signOut(){
        gac?.connect()



        gac?.registerConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
            override fun onConnected(bundle: Bundle?) {
                FirebaseAuth.getInstance().signOut()
                if (gac!!.isConnected()) {
                    Auth.GoogleSignInApi.signOut(gac).setResultCallback { status ->
                        if (status.isSuccess) {
                            Log.v("알림", "로그아웃 성공")
                            pageChange("Login")
                            finish()
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
}