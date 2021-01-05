package com.uos.vcommcerce.mainslide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.UserActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.activity_main.*


class MainTopView {

    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375
    val standardTopViewSize : Int = 130


    //피그마크기1px 당 실제뷰 크기값
    var size_X : Float = 0f
    var size_Y : Float = 0f

    //실제뷰 크기값
    var TopViewSize : Int = 0

    //각 뷰 기본사이즈
    val SearchViewSize: Int = 40;//검색창 크기
    val RecyclerItemSize: Int = 30;//검색 아이템 크기
    val MoveListViewSize = 48;//이동아이콘 크기
    val viewHandleSize = 16;//하단바 손잡이 크기
    var beforeState: MainActivityState = MainActivityState.slideDown1;
    //메인뷰 엑티비티
    lateinit var mainActivity: Activity;
    //메인의 바인딩
    private lateinit var binding: ActivityMainBinding

    //이동뷰 리스트
    var moveItemList : ArrayList<View> = arrayListOf()

    companion object {
        //싱글톤 생성
        var instance = MainTopView()
        //외부에서 받아온 리사이클러 어댑터
        var SearchViewAdapter: SearchAdapter? = null
        //외부에서 받아온 뷰들
        lateinit var TopView: View  //탑뷰
        lateinit var SearchView: EditText   //검색뷰
        lateinit var SearchListView: View;  //검색리스트 뷰
        lateinit var MoveListView: View;     //이동뷰 그룹

        //검색창용 변수및 리스트
        lateinit var originalList: ArrayList<String>
        var filterList: MutableList<String> = mutableListOf<String>()
        var writedWord: String = ""
    }



    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setTopView(topView: View, searchView: CustomEditText, searchListView: View, moveListView: View,moveViewList : ArrayList<ImageView>, searchViewAdapter: SearchAdapter, MainActivity:Activity, Binding : ActivityMainBinding) {
        //메인의 탑뷰
        TopView = topView;
        //탑뷰의 서치뷰
        SearchView = searchView
        //탑뷰의 서치뷰 리스트
        SearchListView = searchListView;
        //탑뷰의 이동뷰
        MoveListView = moveListView;
        //탑뷰의 어댑터
        SearchViewAdapter = searchViewAdapter
        //메인액티비티받아와서 넘겨주기
        SetSize(MainActivity,moveViewList)
        mainActivity = MainActivity
        binding = Binding
        binding.topview = this

        //메인 서치뷰에 텍스트 변경인식 리스너 추가
        searchView.addTextChangedListener(MainTopView.instance.TextChangeListener)
        //백키 누를시 적용될 함수 - 서치리스트뷰 숨기기
        searchView.setCallback { MainTopView.instance.SearchEnd() }
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
            TopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + 3*RecyclerItemSize)).toInt() )
            MoveListView.setHeight(0)
            SearchListView.setHeight(3 * RecyclerItemSize)
            SearchListView.setMarginBottom(0)


        } else {
            TopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + searchItemCount*RecyclerItemSize)).toInt() )
            MoveListView.setHeight(0)
            SearchListView.setHeight(searchItemCount * RecyclerItemSize)
            SearchListView.setMarginBottom(0)
        }
    }



    //검색창 종료함수
    fun SearchEnd(){
        Log.d("beforeState 값3 : ",""+beforeState )
        mainActivityState = beforeState
        TopView.setHeight(TopViewSize)     //하단뷰 크기 설정
        MoveListView.setHeight(MoveListViewSize)
        SearchListView?.setHeight(0)
        SearchListView.setMarginBottom((size_Y*16).toInt())
        SearchView?.clearFocus()
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
        originalList  = ArrayList<String>()
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

        init {
            val inflate: LayoutInflater = LayoutInflater.from(context)
        }
    }

    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            writedWord = SearchView!!.text.toString()
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

    fun SetSize(MainActivity: Activity,moveViewList : ArrayList<ImageView>){

        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        size_Y = (ScreenSize.y / density)/standardSize_Y
        size_X = (ScreenSize.x / density)/standardSize_X
        TopViewSize = (size_Y*standardTopViewSize).toInt()

        TopView.setHeight(TopViewSize)     //하단뷰 크기 설정
        TopView.setPadding(24*size_X.toInt().dp(),0,24*size_X.toInt().dp(),0)

        SearchView.setHeight((size_Y*SearchViewSize).toInt())
        SearchView.setMarginTop((size_Y*10).toInt())

        SearchListView.setHeight(0)
        SearchListView.setMarginBottom((size_Y*16).toInt())

        MoveListView.setHeight((size_Y*MoveListViewSize).toInt())

        moveViewList[0].setHeight((size_Y*MoveListViewSize).toInt())
        moveViewList[0].setWidth((size_Y*MoveListViewSize).toInt())

        moveViewList[1].setHeight((size_Y*MoveListViewSize).toInt())
        moveViewList[1].setWidth((size_Y*MoveListViewSize).toInt())

        moveViewList[2].setHeight((size_Y*MoveListViewSize).toInt())
        moveViewList[2].setWidth((size_Y*MoveListViewSize).toInt())

        moveViewList[3].setHeight((size_Y*MoveListViewSize).toInt())
        moveViewList[3].setWidth((size_Y*MoveListViewSize).toInt())

        ViewAnimation(TopView, 0,  -TopViewSize.dp(), 0, MainActivityState.default)
    }

}

