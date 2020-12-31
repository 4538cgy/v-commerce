package com.uos.vcommcerce.mainslide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.UserActivity
import com.uos.vcommcerce.adapter.mainActivityState
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.util.MainActivityState
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight


class MainTopView {



    //각 뷰 기본사이즈
    val mainSearchViewSize: Int = 35;//검색창
    val mainRecyclerItemSize: Int = 30;//검색 아이템 크기
    val mainViewChangeSize = 60;//이동아이콘
    val mainViewListSize = 20;//하단바 손잡이
    var beforeState: MainActivityState = MainActivityState.slideDown1;
    //메인뷰 엑티비티
    lateinit var mainActivity: Activity;
    //메인의 바인딩
    private lateinit var binding: ActivityMainBinding

    //TOP뷰 기본 사이즈
    val TopViewSize: Int = mainSearchViewSize + mainViewChangeSize + mainViewListSize;

    //이동뷰 리스트
    var moveItemList : ArrayList<View> = arrayListOf()

    companion object {
        //싱글톤 생성
        var instance = MainTopView()
        //외부에서 받아온 리사이클러 어댑터
        var SearchViewAdapter: SearchAdapter? = null
        //외부에서 받아온 뷰들
        lateinit var TopView: View
        lateinit var MainSearchView: EditText
        lateinit var MainSearchListView: View;
        var MainViewChange: View? = null;
        var MainViewListCover : View? = null;
        var MainViewList: View? = null;

        //검색창용 변수및 리스트
        var originalList: ArrayList<String>? = null
        var filterList: MutableList<String>? = null
        var selectedList: List<String>? = null
        var writedWord: String? = null
    }



    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setTopView(topView: View, searchView: EditText, mainSearchListView: View, mainViewChange: View,mainViewListCover:View,mainViewList:View, searchViewAdapter: SearchAdapter,MainActivity:Activity,Binding : ActivityMainBinding) {
        //메인의 탑뷰
        TopView = topView;
        //탑뷰의 서치뷰
        MainSearchView = searchView
        //탑뷰의 서치뷰 리스트
        MainSearchListView = mainSearchListView;
        //탑뷰의 이동뷰
        MainViewChange = mainViewChange;
        //탑뷰의 그리드뷰 커버
        MainViewListCover = mainViewListCover;
        //탑뷰의 그리드뷰
        MainViewList = mainViewList;
        //탑뷰의 어댑터
        SearchViewAdapter = searchViewAdapter
        //메인액티비티받아와서 넘겨주기
        SetSize(MainActivity)
        mainActivity = MainActivity
        binding = Binding
        binding.topview = this
    }

    //상단뷰 1번아이콘 클릭이벤트
    fun IconMove1(view : View) { mainActivity.startActivity(Intent(mainActivity, UserActivity::class.java)) }

    fun SearchEvent(view: View){
        Log.d("검색창 오픈!","검색창오픈!!")
        //메인 상태를 검색으로 변경
        val text = writedWord ?: ""
        search(text)
        searchingViewChange()
    }


    //검색 리스트 변경 함수 - 검색창 열기
    fun searchingViewChange() {
        var searchItemCount = filterList?.size ?: 0
        if(mainActivityState!=MainActivityState.search) {
            beforeState = mainActivityState;
        }
        mainActivityState = MainActivityState.search
        if (searchItemCount < 3) {
            TopView?.setHeight(mainSearchViewSize + 3 * mainRecyclerItemSize)
            MainViewChange?.setHeight(0)
            MainSearchListView?.setHeight(3 * mainRecyclerItemSize)
            MainViewList?.setHeight(0)

        } else {
            TopView?.setHeight(mainSearchViewSize + searchItemCount * mainRecyclerItemSize)
            MainViewChange?.setHeight(0)
            MainSearchListView?.setHeight(searchItemCount * mainRecyclerItemSize)
            MainViewList?.setHeight(0)
        }
    }



    //검색창 종료함수
    fun SearchEnd(){
        Log.d("beforeState 값3 : ",""+beforeState )
        mainActivityState = beforeState
        TopView.setHeight(TopViewSize)
        MainSearchListView?.setHeight(0)
        MainViewChange?.setHeight(mainViewChangeSize)
        MainViewList?.setHeight(mainViewListSize)
        MainSearchView?.clearFocus()

    }


    //뷰이동 슬라이드 다운 함수
    fun TopViewShow(state: MainActivityState = MainActivityState.notChange ) {
        ViewAnimation(TopView, 0, 0, 500,state)
    }



    //뷰이동 슬라이드 업 함수
    fun TopViewHide(state: MainActivityState = MainActivityState.notChange ) {
        SearchEnd()
        ViewAnimation(TopView, 0,  -TopViewSize.dp(), 500, state)
    }

    //초기화
    init {
        originalList = ArrayList<String>()
        filterList = mutableListOf<String>()
        selectedList = listOf<String>()
        writedWord = ""
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

    //서치 어댑터 클래스
    inner class SearchAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
            val view: View = LayoutInflater.from(context).inflate(R.layout.main_search_item, null)
            val text = view.findViewById<TextView>(R.id.main_search_item_title)
            val list = filterList?.get(position)
            view.setHeight(mainRecyclerItemSize);
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

        init {
            val inflate: LayoutInflater = LayoutInflater.from(context)
        }
    }

    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            writedWord = MainSearchView!!.text.toString()
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
        SearchViewAdapter!!.notifyDataSetChanged()
    }

    fun SetSize(MainActivity: Activity){
        TopView.setHeight(TopViewSize)     //하단뷰 크기 설정
        ViewAnimation(TopView, 0,  -TopViewSize.dp(), 0, MainActivityState.default)
    }

}

