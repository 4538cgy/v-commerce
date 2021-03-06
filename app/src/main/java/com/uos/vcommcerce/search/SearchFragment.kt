package com.uos.vcommcerce.search

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.FragmentSearchBinding
import com.uos.vcommcerce.mainslide.mainActivityState
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.activity_main.view.*


class SearchFragment : Fragment() {

    //binding을 바인딩용 객체로생성
    private  lateinit var binding: FragmentSearchBinding
    private lateinit var SearchListAdapter: SearchAdapter


    companion object {
        //검색창용 변수및 리스트
        lateinit var originalList: ArrayList<String>
        var filterList: MutableList<String> = mutableListOf<String>()
        var writedWord: String = ""
    }


    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375
    //검색 아이템 크기
    val RecyclerItemSize: Int = 30;

    //초기화
    init {
        originalList = ArrayList<String>()
        originalList!!.add("채수빈")
        originalList!!.add("박지현")
        originalList!!.add("수지")
        originalList!!.add("남태현")
        originalList!!.add("하성운")
        originalList!!.add("크리스탈")
        originalList!!.add("강승윤")
        originalList!!.add("손나은")
        originalList!!.add("남주혁")
        originalList!!.add("루이")
        originalList!!.add("진영")
        originalList!!.add("슬기")
        originalList!!.add("이해인")
        originalList!!.add("고원희")
        originalList!!.add("설리")
        originalList!!.add("공명")
        originalList!!.add("김예림")
        originalList!!.add("혜리")
        originalList!!.add("웬디")
        originalList!!.add("박혜수")
        originalList!!.add("카이")
        originalList!!.add("진세연")
        originalList!!.add("동호")
        originalList!!.add("박세완")
        originalList!!.add("도희")
        originalList!!.add("창모")
        originalList!!.add("허영지")

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //binding 할당
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        binding.searchfragment = this




        SearchListAdapter = SearchAdapter(container!!.context)




        //해상도 측정
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        //피그마크기1px 당 실제뷰 크기값
        var size_Y = (ScreenSize.y / density)/standardSize_Y
        var size_X = (ScreenSize.x / density)/standardSize_X

        binding.searchLayout.setHeight((size_Y*55).toInt())
        binding.searchLayout.setLMarginTop((size_Y*16).toInt())
        binding.searchLayout.setLMarginLeft((size_X*16).toInt())
        binding.searchLayout.setLMarginRight((size_X*16).toInt())

        binding.backbtn.setHeight((size_Y*48).toInt())
        binding.backbtn.setWidth((size_Y*48).toInt())

        binding.searchBtn.setHeight((size_Y*48).toInt())
        binding.searchBtn.setWidth((size_Y*48).toInt())


        return binding.root
    }




    //검색 리스트 변경 함수 - 검색창 열기
    fun searchingViewChange() {
        var searchItemCount = filterList?.size ?: 0
        if(mainActivityState != MainActivityState.search) {
        }
        mainActivityState = MainActivityState.search
        if (searchItemCount < 3) {
//            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + 3*RecyclerItemSize)).toInt() )
//            Binding.mainSearchList.setHeight(3 * RecyclerItemSize)
//            Binding.mainSearchList.setMarginBottom(0)
        } else {
//            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + searchItemCount*RecyclerItemSize)).toInt() )
//            Binding.mainViewChange.setHeight(0)
//            Binding.mainSearchList.setHeight(searchItemCount * RecyclerItemSize)
//            Binding.mainSearchList.setMarginBottom(0)
        }
    }


    //검색창 종료함수
    fun SearchEnd(){
//        Binding.mainTopView.setHeight(TopViewSize)     //하단뷰 크기 설정
//        Binding.mainViewChange.setHeight(MoveListViewSize)
//        Binding.mainSearchList.setHeight(0)
//        Binding.mainSearchList.setMarginBottom((size_Y*16).toInt())
//        Binding.mainSearch.clearFocus()
    }

    //검색창 최초상태
    fun SearchSet(){

    }


    //서치 어댑터 클래스
    inner class SearchAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
            val view: View = LayoutInflater.from(context).inflate(R.layout.main_search_item, null)
            val text = view.findViewById<TextView>(R.id.main_search_item_title)
            val list = filterList?.get(position)
            view.setHeight(RecyclerItemSize);
            text.text = list;
            return view
        }

        override fun getItem(p0: Int): Any? {
            return null
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return filterList?.size ?: 0
        }

    }


    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
//            writedWord = Binding.mainSearch.text.toString()
            //추출한뒤 writedWord에 집어 넣어줘야함
            val text = writedWord ?: ""
            search(text)
            searchingViewChange()
        }
        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    //검색함수
    fun search(charText: String) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        filterList!!.clear()
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length == 0) {
            for (i in 1..10) {
                filterList!!.add(originalList!![i])
            }
            //filterList!!.addAll(originalList!!)
        } else {
            var check = 0;
            // 리스트의 모든 데이터를 검색한다.
            for (i in originalList!!.indices) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (originalList!![i].toLowerCase().contains(charText) && check < 10) {
                    // 검색된 데이터를 리스트에 추가한다.
                    filterList!!.add(originalList!![i])
                    check++
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        SearchListAdapter.notifyDataSetChanged()
    }

}