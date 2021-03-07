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
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.R
import com.uos.vcommcerce.databinding.FragmentSearchBinding
import com.uos.vcommcerce.mainslide.mainActivityState
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.main_search_item.view.*


class SearchFragment : Fragment() {

    //binding을 바인딩용 객체로생성
    private  lateinit var binding: FragmentSearchBinding
    private lateinit var SearchListAdapter: SearchAdapter


    companion object {
        //검색창용 변수및 리스트
        var topSearchList: ArrayList<String> = arrayListOf<String>()
        //최근 검색 기록
        var beforeSearchList: ArrayList<String> = arrayListOf<String>()
        //전체 목록 리스트
        var allsearchList: ArrayList<String> = arrayListOf<String>()

        //검색된 리스트
        var filterList: MutableList<String> = mutableListOf<String>()

        var writedWord: String = ""
    }

    //검색창 종료함수
    interface searchEnd{
        fun searchEnd(view: View? = null)
    }

    lateinit var searchend : searchEnd


    var size_Y : Float = 0f
    var size_X : Float = 0f

    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375
    //검색 아이템 크기
    val RecyclerItemSize: Int = 40;

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

        //binding 할당
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        binding.searchfragment = this

        searchend  = activity as MainActivity

        SearchListAdapter = SearchAdapter(container!!.context)

        //해상도 측정
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        //피그마크기1px 당 실제뷰 크기값
        size_Y = (ScreenSize.y / density)/standardSize_Y
        size_X = (ScreenSize.x / density)/standardSize_X

        //검색창 뷰 크기
        binding.searchLayout.setHeight((size_Y*55).toInt())
        binding.searchLayout.setLMarginTop((size_Y*16).toInt())

        binding.backbtn.setHeight((size_Y*48).toInt())
        binding.backbtn.setWidth((size_Y*48).toInt())

        binding.searchBtn.setHeight((size_Y*48).toInt())
        binding.searchBtn.setWidth((size_Y*48).toInt())

        //뒤로가기누르면 포커스 제거
        binding.searchView.setCallback {
            binding.searchView.clearFocus()
        }

        binding.searchView.onFocusChangeListener = onFocusChabgeListener
        binding.searchView.addTextChangedListener(TextChangeListener)

        //최근 검색어 뷰 크기
        binding.recentSearch.setHeight((size_Y*325).toInt())
        binding.recentSearch.setLMarginRight((size_X*16).toInt())
        binding.recentSearch.setLMarginLeft((size_X*16).toInt())

        binding.recentSearchText.setHeight((size_Y*47).toInt())
        binding.recentSearchText.setLMarginTop((size_Y*25).toInt())

        //최근 검색 리사이클러뷰 장착
        binding.recentSearchList.adapter = SearchListAdapter

        //찾으시는 상품이 없서요
        binding.notfind.setHeight((size_Y*132).toInt())
        binding.notfind.setLMarginTop((size_Y*25).toInt())

        //이런상품은 어떄요 뷰 크기
        binding.howAbout.setHeight((size_Y*325).toInt())
        binding.howAbout.setLMarginRight((size_X*16).toInt())
        binding.howAbout.setLMarginLeft((size_X*16).toInt())

        binding.howAboutText.setHeight((size_Y*47).toInt())
        binding.howAboutText.setLMarginTop((size_Y*25).toInt())

        SearchSet()

        return binding.root
    }



    //검색창 최초상태 세팅
    fun SearchSet(){
        binding.searchView.setText("");
        binding.recentSearch.visibility = View.GONE;
        binding.notfind.visibility = View.GONE;
        binding.howAbout.visibility = View.VISIBLE;
    }

    //검색창 포커스 옵저빙 리스너
    val onFocusChabgeListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if(hasFocus == true) {
                binding.recentSearch.visibility = View.VISIBLE;
                binding.notfind.visibility = View.GONE;
                binding.howAbout.visibility = View.GONE;
            }else if(binding.searchView.text.toString().length == 0){
                binding.recentSearch.visibility = View.GONE;
                binding.notfind.visibility = View.GONE;
                binding.howAbout.visibility = View.VISIBLE;
            }
        }

    }








//    //검색 리스트 변경 함수 - 검색창 열기
//    fun searchingViewChange() {
//        var searchItemCount = filterList?.size ?: 0
//        if(mainActivityState != MainActivityState.search) {
//        }
//        mainActivityState = MainActivityState.search
//        if (searchItemCount < 3) {
////            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + 3*RecyclerItemSize)).toInt() )
////            Binding.mainSearchList.setHeight(3 * RecyclerItemSize)
////            Binding.mainSearchList.setMarginBottom(0)
//        } else {
////            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + searchItemCount*RecyclerItemSize)).toInt() )
////            Binding.mainViewChange.setHeight(0)
////            Binding.mainSearchList.setHeight(searchItemCount * RecyclerItemSize)
////            Binding.mainSearchList.setMarginBottom(0)
//        }
//    }





    //서치 어댑터 클래스
    inner class SearchAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
            val view: View = LayoutInflater.from(context).inflate(R.layout.main_search_item, null)
            //뷰크기 재설정
            view.search_item_layout.setHeight((RecyclerItemSize*size_Y).toInt());

            view.search_view_simbol.setHeight((24*size_Y).toInt())
            view.search_view_simbol.setWidth((24*size_Y).toInt())
            view.search_view_simbol.setLMarginLeft((16*size_Y).toInt())

            //내용 이식
            view.item_text.text = filterList?.get(position);
            view.item_text.setHeight((22*size_Y).toInt())
            view.item_text.setLMarginLeft((8*size_Y).toInt())

            view.delete_record.setHeight((24*size_Y).toInt())
            view.delete_record.setWidth((24*size_Y).toInt())
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

    //검색창 텍스트 확인기
    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            writedWord = binding.searchView.text.toString()
            Log.d("현재 입력된 글자", writedWord)
            //추출한뒤 writedWord에 집어 넣어줘야함
//            val text = writedWord ?: ""
//            searchingViewChange

            search(writedWord)

        }
        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    //검색함수
    fun search(charText: String) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        filterList!!.clear()

        // 문자 입력이 없을때는 최근검색어를 보여준다.
        if (charText.length == 0) {
            filterList.addAll(beforeSearchList)
        } else {
            // 리스트의 모든 데이터를 검색한다.
            allsearchList.forEach{
                if (it.toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    filterList.add(it)
                }
            }
        }

        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        SearchListAdapter.notifyDataSetChanged()
    }


}