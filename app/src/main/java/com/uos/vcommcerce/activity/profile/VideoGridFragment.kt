package com.uos.vcommcerce.activity.profile

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uos.vcommcerce.Model.UserDataVM
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseFragment
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.FragmentVideoGridBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.datamodel.UserVideoData
import com.uos.vcommcerce.testpackagedeletesoon.TestExoplayerActivity
import com.uos.vcommcerce.util.setHeight


class VideoGridFragment : BaseFragment<FragmentVideoGridBinding>(layoutId = R.layout.fragment_video_grid) {

    //나중에 값이 설정될거라고 lateinit으로 설정
    lateinit var userDataViewModel: UserDataVM
    //lateinit var recyclerGridView :RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.recyclerGridView.layoutManager = GridLayoutManager(requireContext(), 3)

        userDataViewModel = ViewModelProvider(this).get(UserDataVM::class.java)
        userDataViewModel.gridData.observe(this, Observer {
            var adapter = RecyclerProfileVideoAdapter(requireContext(),R.layout.video_grid_item ,it)
            binding.recyclerGridView.adapter = adapter
            var lowcount: Int = (adapter.getItemCount() / 3 + 1)
            binding.recyclerGridView.setHeight(222 * lowcount)
        })

        return binding.root
    }
}


