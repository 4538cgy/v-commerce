package com.uos.vcommcerce.mainupside

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
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.UserActivity
import com.uos.vcommcerce.topBottomState
import com.uos.vcommcerce.util.TopBottomState
import com.uos.vcommcerce.util.dp
import com.uos.vcommcerce.util.setHeight


class MainTopSlideDown {



    //각 뷰 기본사이즈
    val mainSearchViewSize: Int = 35;//검색창
    val mainRecyclerItemSize: Int = 30;//검색 아이템 크기
    val mainViewChangeSize = 60;//이동아이콘
    val mainViewListSize = 20;//하단바 손잡이

    //TOP뷰 전체크기 최대 최소
    val TopMin: Int = 0;
    val TopMax: Int = mainSearchViewSize + mainViewChangeSize + mainViewListSize;

    //이동뷰 리스트
    var moveItemList : ArrayList<View> = arrayListOf()

    companion object {
        var instance = MainTopSlideDown()
        //외부에서 받아온 리사이클러 어댑터
        var SearchViewAdapter: SearchAdapter? = null
        //외부에서 받아온 뷰들
        var TopView: View? = null;
        var MainSearchView: EditText? = null
        var MainSearchListView: View? = null;
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
    fun setTopView(topView: View, searchView: EditText, mainSearchListView: View, mainViewChange: View,mainViewListCover:View,mainViewList:View, searchViewAdapter: SearchAdapter) {
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
    }

    fun setMoveItem(mainActivity : Activity,moveItem1 : View,moveItem2 : View,moveItem3 : View,moveItem4 : View,moveItem5 : View){
        val moveitemListner : View.OnClickListener = object :View.OnClickListener{
            override fun onClick(v: View?) {
                var i =0;
                for(i in 0..moveItemList.count()){ moveItemList[i].equals(v);break }
                //이동 아이콘 클릭시 이동할 위치 할당하면됨
                when (i){
                    0->  mainActivity.startActivity(Intent(mainActivity, UserActivity::class.java))

                }
            }
        }
        moveItemList.add(moveItem1)
        moveItem1.setOnClickListener(moveitemListner)
        moveItemList.add(moveItem2)
        moveItem2.setOnClickListener(moveitemListner)
        moveItemList.add(moveItem3)
        moveItem3.setOnClickListener(moveitemListner)
        moveItemList.add(moveItem4)
        moveItem4.setOnClickListener(moveitemListner)
        moveItemList.add(moveItem5)
        moveItem5.setOnClickListener(moveitemListner)
    }

    //검색창 온클릭 이벤트 리스너
    val mainTopSearchViewOnclickListener = object : View.OnClickListener { override fun onClick(v: View?) {} }
    //검색창 터치 이벤트 리스너 - 창닫기
    val mainTopSearchViewOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {

            when(topBottomState){
                TopBottomState().slideUpMid-> MainBottomSlideUp.instance.SlideDown();
            }
            topBottomState = TopBottomState().search
            val text = writedWord ?: ""
            search(text)
            searchingViewChange()
            return false;
        }
    }

    //뷰이동 터치 이벤트 리스너 - 창닫기
    val mainTopViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            SlideUp()
        }
    }


    //검색 리스트 변경 함수 - 검색창 열기
    fun searchingViewChange() {
        var searchItemCount = filterList?.size ?: 0
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
    fun SearchUp(){
        topBottomState = TopBottomState().slideDown;
        TopView?.setHeight(mainSearchViewSize + mainViewChangeSize + mainViewListSize)
        MainSearchListView?.setHeight(0)
        MainViewChange?.setHeight(mainViewChangeSize)
        MainViewList?.setHeight(mainViewListSize)
        MainSearchView?.clearFocus()

    }


    //뷰이동 슬라이드 다운 함수
    fun SlideDown() {
        //기본상태 or 상단바열려있을시 상닫바 닫기 + 슬라이드 업 하기
        if (topBottomState == TopBottomState().none) {

            //애니메이션 생성
            val animate: TranslateAnimation =TranslateAnimation(0f,0f,-TopMax.dp().toFloat(),0f)
            animate.duration = 500;
            animate.fillAfter = true;

            //애니메이션 리스너 장착
            animate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    topBottomState = TopBottomState().slideDown
                }
            })

            //상태를 무빙으로 변경 -> 종료후 slideDown
            topBottomState = TopBottomState().moving;

            TopView?.startAnimation(animate)
        }
    }





    //뷰이동 슬라이드 업 함수
    fun SlideUp() {
        if (topBottomState == TopBottomState().slideDown) {
            //애니메이션 생성
            var animate: TranslateAnimation =TranslateAnimation(0f,0f,0f,-TopMax.dp().toFloat())
            animate.duration = 500;
            animate.fillAfter = true;

            animate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    //slideDown 상태일매나 상태 none 설정
                    topBottomState = TopBottomState().none
                }
            })
            //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 none
            topBottomState = TopBottomState().moving
            //애니메이션 실행 -> 종료후 뷰 크기 변경
            TopView?.startAnimation(animate)
        }
    }

    //뷰이동 슬라이드 업 함수
    fun init() {
        //애니메이션 생성
        var animate: TranslateAnimation = TranslateAnimation(0f, 0f, 0f, -TopMax.dp().toFloat())
        animate.duration = 0;
        animate.fillAfter = true;
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                //slideDown 상태일매나 상태 none 설정
                topBottomState = TopBottomState().none
            }
        })
        //상태를 무빙으로 변경 -> 종료후 다른상태가 아닐시 none
        topBottomState = TopBottomState().moving
        MainSearchListView?.setHeight(0)
        //애니메이션 실행 -> 종료후 뷰 크기 변경
        TopView?.startAnimation(animate)
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


}

