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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.Model.UserDataVM
import com.uos.vcommcerce.databinding.FragmentHistoryBinding
import com.uos.vcommcerce.databinding.RecyclerHistoryItemBinding
import kotlinx.android.synthetic.main.fragment_history.*


data class HistoryData(
    var historyTitle : String,
    var historyContent : String
)

class HistoryFragment : Fragment() {
//    val DataList = listOf(
//        HistoryData("history1", "1번"),
//        HistoryData("history2-1", "2.1번"),
//        HistoryData("history2-2", "2.2번"),
//        HistoryData("history3", "3번"),
//        HistoryData("history4", "4번"),
//        HistoryData("history5", "5번"),
//        HistoryData("history6", "6번"),
//        HistoryData("history7-1", "7.1번"),
//        HistoryData("history7-2", "7.2번"),
//        HistoryData("history7-3", "7.3번"),
//        HistoryData("history8", "8번"),
//        HistoryData("history9", "9번"),
//        HistoryData("history10", "10번"),
//        HistoryData("history11", "11번"),
//        HistoryData("history12-1", "12.1번"),
//        HistoryData("history12-2", "12.2번"),
//        HistoryData("history13", "13번"),
//        HistoryData("history14", "14번"),
//        HistoryData("history15", "15번"),
//        HistoryData("history16", "16번"),
//        HistoryData("history17-1", "17.1번"),
//        HistoryData("history17-2", "17.2번"),
//        HistoryData("history17-3", "17.3번"),
//        HistoryData("history18", "18번"),
//        HistoryData("history19", "19번"),
//        HistoryData("history20-여까지", "끄읏-")
//    )
    lateinit var binding:FragmentHistoryBinding
    lateinit var userDataViewModel: UserDataVM
    //lateinit var historyView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history,container, false)

        binding.recyclerHistoryView.layoutManager = LinearLayoutManager(requireContext())
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


    inner class RecyclerHistoryViewHolder(val binding: RecyclerHistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : HistoryData){
            binding.historyitem = data
        }
    }

    inner class RecyclerHistoryViewAdapter(var data:List<HistoryData> ,val context: Context) : RecyclerView.Adapter<RecyclerHistoryViewHolder>() {
        //생성하는부분
//        var data = listOf<HistoryData>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHistoryViewHolder {
            val binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
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