package com.uos.vcommcerce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerGridViewHolder(v : View) : RecyclerView.ViewHolder(v){}
class RecyclerGridViewAdapter : RecyclerView.Adapter<RecyclerGridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGridViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.recycler_grid_item, parent, false)
        return RecyclerGridViewHolder(cellForRow);
    }

    override fun onBindViewHolder(holder: RecyclerGridViewHolder, position: Int) {
    }

    override fun getItemCount() = 15
}