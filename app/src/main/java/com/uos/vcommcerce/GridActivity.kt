package com.uos.vcommcerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_grid_view.*

class GridActivity : AppCompatActivity(){
    val DataList = arrayListOf(
        GridData(R.drawable.ic_launcher_background, "0번"),
        GridData(R.drawable.ic_launcher_background, "1번"),
        GridData(R.drawable.btn_signin_facebook, "2번"),
        GridData(R.drawable.ic_launcher_background, "3번"),
        GridData(R.drawable.ic_launcher_background, "4번"),
        GridData(R.drawable.ic_launcher_background, "5번"),
        GridData(R.drawable.com_facebook_auth_dialog_cancel_background, "6번"),
        GridData(R.drawable.ic_launcher_background, "7번"),
        GridData(R.drawable.ic_launcher_background, "8번"),
        GridData(R.drawable.com_facebook_button_icon_white, "9번"),
        GridData(R.drawable.ic_launcher_background, "10번"),
        GridData(R.drawable.com_facebook_tooltip_black_xout, "11번"),
        GridData(R.drawable.common_google_signin_btn_text_disabled, "12번"),
        GridData(R.drawable.ic_launcher_background, "13번"),
        GridData(R.drawable.ic_launcher_background, "14번"),
        GridData(R.drawable.messenger_bubble_large_white, "15번"),
        GridData(R.drawable.ic_launcher_background, "16번"),
        GridData(R.drawable.messenger_button_white_bg_selector, "17번"),
        GridData(R.drawable.ic_launcher_background, "18번"),
        GridData(R.drawable.ic_launcher_background, "19번"),
        GridData(R.drawable.ic_launcher_background, "20번"),
        GridData(R.drawable.ic_launcher_background, "21번"),
        GridData(R.drawable.ic_launcher_background, "22번"),
        GridData(R.drawable.ic_launcher_background, "23번"),
        GridData(R.drawable.ic_launcher_background, "24번"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_grid_view)

        //recyclerGridView.layoutManager = LinearLayoutManager(this)
        recyclerGridView.layoutManager = GridLayoutManager(this,3)
        recyclerGridView.adapter = RecyclerGridViewAdapter(DataList, this)
    }
}