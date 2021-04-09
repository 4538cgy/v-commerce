package com.uos.vcommcerce.search

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.TextWatcherAdapter
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseFragment
import com.uos.vcommcerce.base.BaseRecyclerAdapter
import com.uos.vcommcerce.databinding.FragmentSearchBinding
import com.uos.vcommcerce.databinding.MainSearchItemBinding
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.main_search_item.view.*


class SearchFragment(var displaySize: ObservableField<DisplaySize>,searchEndListener : I_searchEnd) : BaseFragment<FragmentSearchBinding>(layoutId = R.layout.fragment_search) {

    //검색리스트 어댑터
    private lateinit var SearchListAdapter: SearchAdapter<String,MainSearchItemBinding>
    //메인의 검색창 종료 리스너
    var searchend: I_searchEnd = searchEndListener

    companion object {
        //최근 검색 기록
        var beforeSearchList: ArrayList<String> = arrayListOf<String>()
        var topSearchList: ArrayList<String> = arrayListOf<String>()
        //전체 목록 리스트
        var allsearchList: ArrayList<String> = arrayListOf<String>()
        //검색된 리스트
    }
    //초기화 나중엔 파베에서 받아오는걸루
    init {
        allsearchList!!.add("스타킹")
        allsearchList!!.add("박지현")
        allsearchList!!.add("수지")
        allsearchList!!.add("남태현")
        allsearchList!!.add("하성운")
        allsearchList!!.add("크리스탈")
        allsearchList!!.add("강승윤")
        allsearchList!!.add("손나은")
        allsearchList!!.add("남주혁")
        allsearchList!!.add("루이")
        allsearchList!!.add("진영")
        allsearchList!!.add("슬기")
        allsearchList!!.add("이해인")
        allsearchList!!.add("고원희")
        allsearchList!!.add("설리")
        allsearchList!!.add("공명")
        allsearchList!!.add("김예림")
        allsearchList!!.add("혜리")
        allsearchList!!.add("웬디")
        allsearchList!!.add("박혜수")
        allsearchList!!.add("카이")
        allsearchList!!.add("진세연")
        allsearchList!!.add("동호")
        allsearchList!!.add("박세완")
        allsearchList!!.add("도희")
        allsearchList!!.add("창모")
        allsearchList!!.add("허영지")


        beforeSearchList.add("이전 검색목록1")
        beforeSearchList.add("이전 검색목록2")
        beforeSearchList.add("이전 검색목록3")
        beforeSearchList.add("이전 검색목록4")
        beforeSearchList.add("이전 검색목록5")
        beforeSearchList.add("이전 검색목록6")
        beforeSearchList.add("이전 검색목록7")
        beforeSearchList.add("이전 검색목록8")
        beforeSearchList.add("이전 검색목록9")
        beforeSearchList.add("이전 검색목록10")
        beforeSearchList.add("이전 검색목록11")
        beforeSearchList.add("이전 검색목록12")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.searchfragment = this
        binding.displaySize = displaySize

        SearchListAdapter = SearchAdapter(R.layout.main_search_item, beforeSearchList,allsearchList,container!!.context,displaySize)
        //뒤로가기누르면 포커스 제거
        binding.searchView.setCallback { binding.searchView.clearFocus() }

        binding.searchView.onFocusChangeListener = onFocusChabgeListener
        binding.searchView.addTextChangedListener(TextChangeListener)

        //최근 검색 리사이클러뷰 장착
        binding.recentSearchList.adapter = SearchListAdapter
        binding.recentSearchList.layoutManager = LinearLayoutManager(context)
        SearchSet()
        return binding.root
    }

    //검색창 최초상태 세팅
    fun SearchSet() {
        binding.searchView.setText("");
        binding.recentSearch.visibility = View.GONE;
        binding.notfind.visibility = View.GONE;
        binding.howAbout.visibility = View.VISIBLE;
        SearchListAdapter.search( binding.searchView.text.toString())
    }

    //검색창 포커스 옵저빙 리스너
    //겁색중일때 -> 검색창on
    //검색을끝냈을때 -> 글자수가0개이면 검색창off
    val onFocusChabgeListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus == true) {
                binding.recentSearch.visibility = View.VISIBLE;
                binding.notfind.visibility = View.GONE;
                binding.howAbout.visibility = View.GONE;
            } else if (binding.searchView.text.toString().length == 0) {
                binding.recentSearch.visibility = View.GONE;
                binding.notfind.visibility = View.GONE;
                binding.howAbout.visibility = View.VISIBLE;
            }
        }

    }

    //검색창 텍스트 확인기
    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            SearchListAdapter.search(binding.searchView.text.toString())
            Log.d("현재 입력된 글자", binding.searchView.text.toString())
        }

        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

    }

}

class SearchAdapter<item: String ,viewBinding : MainSearchItemBinding>(layoutId:Int,recentSearchList:ArrayList<item>,var allProdutList: ArrayList<item>,context: Context ,var displaySize: ObservableField<DisplaySize>) : BaseRecyclerAdapter<item,viewBinding>(layoutId,recentSearchList) {

    //itemList = 최근검색 기록
    //allProdutList = 모든 제품 리스트
    var filterList: ArrayList<item> = ArrayList<item>()

    override fun getItemCount(): Int {
        return filterList.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(filterList[position])
        holder.binding.displaySize = displaySize
    }

    //검색함수
    fun search(charText: String) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        filterList!!.clear()

        // 문자 입력이 없을때는 모든리스트를 문자입력이있을때는 포함된 단어리스트를 생성
        if (charText.length == 0) { filterList.addAll(itemList)
        } else {
            allProdutList.forEach {
                if (it.toLowerCase().contains(charText)) { filterList.add(it) }
            }
        }

        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        notifyDataSetChanged()
    }

}