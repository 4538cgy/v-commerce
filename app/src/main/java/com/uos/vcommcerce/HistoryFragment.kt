package com.uos.vcommcerce

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.Model.UserDataVM
import com.uos.vcommcerce.databinding.FragmentHistoryBinding
import com.uos.vcommcerce.databinding.VideoGridItemBinding
import com.uos.vcommcerce.model.UserVideoData
import kotlinx.android.synthetic.main.fragment_history.*




class HistoryFragment : Fragment() {

    lateinit var binding:FragmentHistoryBinding
    lateinit var userDataViewModel: UserDataVM
    //lateinit var historyView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history,container, false)

        binding.recyclerHistoryView.layoutManager = GridLayoutManager(requireContext(),3)
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


    inner class RecyclerHistoryViewHolder(val binding: VideoGridItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : UserVideoData){
            binding.griditem = data
            binding.executePendingBindings()
        }
    }

    inner class RecyclerHistoryViewAdapter(var data:List<UserVideoData>, val context: Context) : RecyclerView.Adapter<RecyclerHistoryViewHolder>() {
        //생성하는부분
//        var data = listOf<HistoryData>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHistoryViewHolder {
            val binding = VideoGridItemBinding.inflate(LayoutInflater.from(context),parent,false)
            //val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_history_item, parent, false)
            return RecyclerHistoryViewHolder(binding);
        }
        //보여줄 개수
        override fun getItemCount():Int = data.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerHistoryViewHolder, position: Int) {
            holder.onBind(data[position])
        }
    }
}