package com.uos.vcommcerce.TestPackageDeleteSoon

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.uos.vcommcerce.R
import kotlinx.android.synthetic.main.test_exoplayer_view.*

class TestExoplayerActivity : AppCompatActivity(){


    var backKeyPressedTime = 0
    private var player:SimpleExoPlayer ? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var mediaItem : MediaItem ? = null
    private var mediaPath = "gs://sns-kotlin.appspot.com/1분 동안 세계에서 일어나는 일들!!.mp4"
    private var mediaPath2 = "https://firebasestorage.googleapis.com/v0/b/sns-kotlin.appspot.com/o/images%2FIMAGE_20200908_182705_.png?alt=media&token=9347344e-6ba8-4231-93a2-8f61593f5c3f"
    private var mediaPath3 = "https://www.youtube.com/embed/oYnfsg-l0KM"
    private var mediaPath4 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_exoplayer_view)

        //initializePlayerDefault()

        initializePlayer()
        attachPlayerView()
        initialVideoUrl(mediaPath4)
        preparePlayer()
        playPlayer()



    }

    /*
    fun initializePlayerDefault(){

        if(player == null){

            player = ExoPlayerFactory.newSimpleInstance(this)
            test_exoplayer_view_playerview.player = player

            val defaultHttpDataSourceFactory = DefaultHttpDataSourceFactory(getString(R.string.app_name))
            val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(Uri.parse(mediaPath4))
            player!!.prepare(mediaSource)

            test_exoplayer_view_playerview.controllerShowTimeoutMs = 0
        }

    }


     */

    override fun onStop() {
        super.onStop()

        player?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
        //
    }


    override fun onBackPressed() {

        var curTime = System.currentTimeMillis()
        var gapTime = curTime - backKeyPressedTime

        //터치 체크
        if (0<=gapTime && 2000 >= gapTime){
            super.onBackPressed()
        }else{
            backKeyPressedTime = curTime.toInt()
            showSettingPopup()
        }
    }

    //뒤로가기 눌렀을 때 종료할건지 판단
    fun showSettingPopup(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.pop_up_quit_check,null)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("앱을 종료하시겠습니까?")
            .setPositiveButton("네") {
                    dialog , which ->
                finishAffinity()
            }
            .setNeutralButton("아니오",null)

            .create()


        alertDialog.setView(view)
        alertDialog.show()
    }



    //Creating the player
    fun initializePlayer(){
        player = SimpleExoPlayer.Builder(applicationContext).build()

    }

    fun attachPlayerView(){
        test_exoplayer_view_playerview.setPlayer(player)
    }

    //Video Initial from Url
    fun initialVideoUrl(mediaItem: String){
        initialVideoUri(Uri.parse(mediaItem.toString()))
    }

    //Video Initial from Uri
    fun initialVideoUri(uri : Uri){
        mediaItem = MediaItem.fromUri(uri)
        setMedia(mediaItem)
    }

    //Set Media
    fun setMedia(mediaItem : MediaItem?){
        if (mediaItem != null) {
            player?.setMediaItem(mediaItem)
        }
    }

    //Set Player state
    fun preparePlayer(){
        player?.prepare()
    }

    //Set Player play
    fun playPlayer(){
        player?.play()
    }



}