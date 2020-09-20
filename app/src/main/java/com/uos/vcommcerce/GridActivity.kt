package com.uos.vcommcerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_grid_view.*

class GridActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_grid_view)

        recyclerGridView.layoutManager = LinearLayoutManager(this)
        recyclerGridView.adapter = RecyclerGridViewAdapter()
    }
}