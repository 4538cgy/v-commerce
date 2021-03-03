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
import com.uos.vcommcerce.SettingActivity
import com.uos.vcommcerce.profile.UserActivity
import com.uos.vcommcerce.databinding.ActivityMainBinding
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.mainslide.ViewAnimation
import com.uos.vcommcerce.util.*
import kotlinx.android.synthetic.main.activity_main.*


class MainTopView {

    //피그마 기준 값
    var standardSize_Y : Int = 770
    var standardSize_X : Int = 375
    val standardTopViewSize : Int = 55


    //피그마크기1px 당 실제뷰 크기값
    var size_X : Float = 0f
    var size_Y : Float = 0f

    //실제뷰 크기값
    var TopViewSize : Int = 0

    //각 뷰 기본사이즈
    val SearchViewSize: Int = 40;//검색창 크기
    val RecyclerItemSize: Int = 30;//검색 아이템 크기
    val MoveListViewSize = 55;//이동아이콘 크기
    val viewHandleSize = 16;//하단바 손잡이 크기


    companion object {
        //검색창용 변수및 리스트
        lateinit var originalList: ArrayList<String>
        var filterList: MutableList<String> = mutableListOf<String>()
        var writedWord: String = ""
    }

    private lateinit var Binding: ActivityMainBinding
    private lateinit var MainActivity: Activity
    private lateinit var SearchListAdapter: SearchAdapter


    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun getMainActivity(binding: ActivityMainBinding, mainActivity: Activity){
        //메인엑티비티뷰 바인드 캐싱후 자신을 연결하기
        Binding = binding
        Binding.topview = this
        //메인엑티비티 캐싱하기
        MainActivity = mainActivity

        //검색리스트 어댑터 설정및 할당
        SearchListAdapter = SearchAdapter(context = MainActivity)
        Binding.mainSearchList.adapter = SearchListAdapter


        //메인바인드를가져왓다 -> 메인 뷰 를가져왓다고 이해가능
        //메인뷰의 크기를 피그마와 동일하도록 바꿈
        val display = MainActivity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var ScreenSize: Point = size
        var density = Resources.getSystem().displayMetrics.density

        size_Y = (ScreenSize.y / density)/standardSize_Y
        size_X = (ScreenSize.x / density)/standardSize_X
        TopViewSize = (size_Y*standardTopViewSize).toInt()

        //탑뷰 전체크기 수정
        Binding.mainTopView.setHeight(TopViewSize)     //하단뷰 크기 설정

        //검색뷰 크기 설정
        Binding.mainSearch.setHeight(0)
        //검색 리스트 크기 설정
        Binding.mainSearchList.setHeight(0)

        //아이콘레이아웃 크기설정
        Binding.mainViewChange.setHeight((size_Y*MoveListViewSize).toInt())

        //각 아이템 크기 성정
        //moveItem1 => activityMainImageButtonProfile
        Binding.activityMainImagebuttonProfile.setHeight((size_Y*MoveListViewSize).toInt())
        Binding.activityMainImagebuttonProfile.setWidth((size_Y*MoveListViewSize).toInt())

        Binding.activityMainImagebuttonVideoList.setHeight((size_Y*MoveListViewSize).toInt())
        Binding.activityMainImagebuttonVideoList.setWidth((size_Y*MoveListViewSize).toInt())

        Binding.activityMainImagebuttonCart.setHeight((size_Y*MoveListViewSize).toInt())
        Binding.activityMainImagebuttonCart.setWidth((size_Y*MoveListViewSize).toInt())

        //movewItem4 => activityMainImageButtonSetting
        Binding.activityMainImagebuttonSearch.setHeight((size_Y*MoveListViewSize).toInt())
        Binding.activityMainImagebuttonSearch.setWidth((size_Y*MoveListViewSize).toInt())


    }

    //상단뷰 1번아이콘 클릭이벤트
    fun IconMove1(view : View) { MainActivity.startActivity(Intent(MainActivity, UserActivity::class.java)) }
    //4번 아이콘
    fun IconMove4(view : View) { MainActivity.startActivity(Intent(MainActivity, SettingActivity::class.java))}

    //검색창 클릭
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
        }
        mainActivityState = MainActivityState.search
        if (searchItemCount < 3) {
            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + 3*RecyclerItemSize)).toInt() )
            Binding.mainViewChange.setHeight(0)
            Binding.mainSearchList.setHeight(3 * RecyclerItemSize)
            Binding.mainSearchList.setMarginBottom(0)


        } else {
            Binding.mainTopView.setHeight((size_Y*(SearchViewSize + 10 + viewHandleSize + searchItemCount*RecyclerItemSize)).toInt() )
            Binding.mainViewChange.setHeight(0)
            Binding.mainSearchList.setHeight(searchItemCount * RecyclerItemSize)
            Binding.mainSearchList.setMarginBottom(0)
        }
    }



    //검색창 종료함수
    fun SearchEnd(){
        Binding.mainTopView.setHeight(TopViewSize)     //하단뷰 크기 설정
        Binding.mainViewChange.setHeight(MoveListViewSize)
        Binding.mainSearchList.setHeight(0)
        Binding.mainSearchList.setMarginBottom((size_Y*16).toInt())
        Binding.mainSearch.clearFocus()
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

    }

    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            writedWord = Binding.mainSearch.text.toString()
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

