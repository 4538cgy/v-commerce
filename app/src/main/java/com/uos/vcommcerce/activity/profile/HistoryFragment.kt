package com.uos.vcommcerce.activity.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.Model.UserDataVM
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseFragment
import com.uos.vcommcerce.databinding.FragmentHistoryBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.datamodel.UserVideoData
import com.uos.vcommcerce.util.setHeight


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(layoutId = R.layout.fragment_history) {

    lateinit var userDataViewModel: UserDataVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.recyclerHistoryView.layoutManager = GridLayoutManager(requireContext(), 3)

        userDataViewModel = ViewModelProvider(this).get(UserDataVM::class.java)
        userDataViewModel.historyData.observe(this, Observer {
            var adapter = RecyclerProfileVideoAdapter(requireContext(),R.layout.video_grid_item ,it)
            binding.recyclerHistoryView.adapter = adapter
            var lowcount: Int = (adapter.getItemCount() / 3 + 1)
            binding.recyclerHistoryView.setHeight(222 * lowcount)
        })

        return binding.root
    }

}