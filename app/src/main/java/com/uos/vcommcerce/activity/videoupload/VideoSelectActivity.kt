package com.uos.vcommcerce.activity.videoupload


import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Instances.query
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.ActivityVideoUploadBinding
import com.uos.vcommcerce.model.Video
import java.util.concurrent.TimeUnit


class VideoSelectActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoUploadBinding
    val videoList = mutableListOf<Video>()
    private val RECORD_REQUEST_CODE = 1000



    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_upload)
        binding.activityselectvideo = this@VideoSelectActivity




        setUpPermission()






    }

    fun test(view: View){
        videoList.forEach {
            print("으아아아아아앜"+it.toString())
        }
        println("으ㅜ아아아아아아앜" + videoList.size)
    }


    fun setUpPermission(){
        val permission = ContextCompat.checkSelfPermission(binding.root.context, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("error", "Permission to record denied")
            makeRequest()


        }else{
            getVideoList()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            RECORD_REQUEST_CODE ->{
//                if(grantResults.isNotEmpty()
//                            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(binding.root.context,"권한 거부됨",Toast.LENGTH_LONG).show()

                }else{

                }
                return
            }
        }


    }



    fun getVideoList() {


        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

// Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES).toString()
        )

// Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"


        val query = binding.root.context.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder)

        println("으아아아아아아앜 쿼리의 길이에요!" + query.toString())

        query.use { cursor ->
            // Cache column indices.
            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor?.moveToNext()!!) {
                // Get values of columns for a given video.
                val id = cursor?.getLong(idColumn!!)
                val name = cursor?.getString(nameColumn!!)
                val duration = cursor?.getInt(durationColumn!!)
                val size = cursor?.getInt(sizeColumn!!)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id!!
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(contentUri, name, duration, size)
            } }





    }
}
