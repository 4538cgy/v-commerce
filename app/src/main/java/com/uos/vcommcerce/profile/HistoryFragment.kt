package com.uos.vcommcerce.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.datamodel.user.UserDataVM
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseFragment
import com.uos.vcommcerce.databinding.FragmentHistoryBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.datamodel.user.UserVideoDTO
import com.uos.vcommcerce.util.setHeight


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(
    layoutId = R.layout.fragment_history
) {

    lateinit var userDataViewModel: UserDataVM
    //lateinit var historyView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.recyclerHistoryView.layoutManager = GridLayoutManager(requireContext(), 3)
        userDataViewModel = ViewModelProvider(this).get(UserDataVM::class.java)
        userDataViewModel.historyData.observe(this, Observer {
            binding.recyclerHistoryView.adapter = RecyclerHistoryViewAdapter(it, requireContext())
        })
        //binding.recyclerHistoryView.adapter = RecyclerHistoryViewAdapter(DataList,requireContext())

        val fragmentView = binding.root
        //val fragmentView = inflater.inflate(R.layout.fragment_history, null)
        //historyView = fragmentView.findViewById(R.id.recyclerHistoryView)
        //historyView.layoutManager = LinearLayoutManager(requireContext())
        //historyView.adapter = RecyclerHistoryViewAdapter(DataList, requireContext())
        return fragmentView
    }

    //그리드 데이터
    // inner class HistoryData(val text1:String, val text2:String)


    inner class RecyclerHistoryViewHolder(val binding: VideoGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: UserVideoDTO) {
            binding.griditem = data
            binding.executePendingBindings()
        }
    }

    inner class RecyclerHistoryViewAdapter(var data: List<UserVideoDTO>, val context: Context) :
        RecyclerView.Adapter<RecyclerHistoryViewHolder>() {
        //생성하는부분
//        var data = listOf<HistoryData>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerHistoryViewHolder {
            val binding = VideoGridItemBinding.inflate(LayoutInflater.from(context), parent, false)
            //val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_history_item, parent, false)
            return RecyclerHistoryViewHolder(binding);
        }

        //보여줄 개수
        override fun getItemCount(): Int = data.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerHistoryViewHolder, position: Int) {
            holder.onBind(data[position])
            //페이지 높이 증가
            var lowcount: Int = getItemCount() / 3 + 1
            binding.recyclerHistoryView.setHeight(222 * lowcount)
        }
    }
}