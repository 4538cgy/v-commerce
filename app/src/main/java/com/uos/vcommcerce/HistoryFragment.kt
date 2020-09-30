package com.uos.vcommcerce

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.recycler_history_item.view.*


class HistoryFragment : Fragment() {
    val DataList = arrayListOf(
        HistoryData("history1", "1번"),
        HistoryData("history2-1", "2.1번"),
        HistoryData("history2-2", "2.2번"),
        HistoryData("history3", "3번"),
        HistoryData("history4", "4번"),
        HistoryData("history5", "5번"),
        HistoryData("history6", "6번"),
        HistoryData("history7-1", "7.1번"),
        HistoryData("history7-2", "7.2번"),
        HistoryData("history7-3", "7.3번"),
        HistoryData("history8", "8번"),
        HistoryData("history9", "9번"),
        HistoryData("history10", "10번"),
        HistoryData("history11", "11번"),
        HistoryData("history12-1", "12.1번"),
        HistoryData("history12-2", "12.2번"),
        HistoryData("history13", "13번"),
        HistoryData("history14", "14번"),
        HistoryData("history15", "15번"),
        HistoryData("history16", "16번"),
        HistoryData("history17-1", "17.1번"),
        HistoryData("history17-2", "17.2번"),
        HistoryData("history17-3", "17.3번"),
        HistoryData("history18", "18번"),
        HistoryData("history19", "19번"),
        HistoryData("history20-여까지", "끄읏-")
    )

    lateinit var historyView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_history, null)
        historyView = fragmentView.findViewById(R.id.recyclerHistoryView)
        historyView.layoutManager = LinearLayoutManager(requireContext())
        historyView.adapter = RecyclerHistoryViewAdapter(DataList, requireContext())

        return fragmentView
    }


    //그리드 데이터
    inner class HistoryData(val text1:String, val text2:String)


    inner class RecyclerHistoryViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val text1 = v.history_text1
        val text2 = v.history_text2
    }

    inner class RecyclerHistoryViewAdapter(val DataList:ArrayList<HistoryData>, val context: Context) : RecyclerView.Adapter<RecyclerHistoryViewHolder>() {
        //생성하는부분
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHistoryViewHolder {
            val cellForRow = LayoutInflater.from(context).inflate(R.layout.recycler_history_item, parent, false)
            return RecyclerHistoryViewHolder(cellForRow);
        }
        //보여줄 개수
        override fun getItemCount() = DataList.size

        //수정하는부분
        override fun onBindViewHolder(holder: RecyclerHistoryViewHolder, position: Int) {
            val data = DataList[position]
            holder.text1.text = DataList[position].text1
            holder.text2.text = DataList[position].text2

        }
    }
}