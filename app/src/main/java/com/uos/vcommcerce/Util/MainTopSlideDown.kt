package com.uos.vcommcerce.Util

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.MainActivity
import com.uos.vcommcerce.Model.MainSearchItem
import com.uos.vcommcerce.R
import com.uos.vcommcerce.isTopViewOpen
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_search_item.view.*

class MainTopSlideDown : View.OnClickListener, View.OnTouchListener {

    val BottomMin: Int = 100;
    val BottomMax: Int = 300;
    val mainViewChangeSize = 60;
    val recyclerItemSize: Int = 30;
    val SearchViewSize: Int = 40;

    companion object {
        var instance = MainTopSlideDown()
        var TopView: View? = null;
        var MainSearchListView: View? = null;
        var MainViewChange: View? = null;
        var MainTopDragView: View? = null;

        //검색창용 변수및 리스트
        var originalList: ArrayList<String>? = null
        var filterList: MutableList<String>? = null
        var selectedList: List<String>? = null
        var writedWord: String? = null
        //외부에서 받아오기
        var SearchViewAdapter : SearchAdapter? = null
        var SearchView : EditText? = null
    }

    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setTopView(topView: View,searchView : EditText,mainSearchListView: View, mainViewChange: View,mainTopDragView: View,searchViewAdapter : SearchAdapter) {
        //메인의 탑뷰
        TopView = topView;
        //탑뷰의 서치뷰
        SearchView = searchView
        //탑뷰의 서치뷰 리스트
        MainSearchListView = mainSearchListView;
        //탑뷰의 이동뷰
        MainViewChange = mainViewChange;
        //탑뷰의 드래그뷰
        MainTopDragView = mainTopDragView;
        //
        SearchViewAdapter =searchViewAdapter
    }

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





    //검색뷰 온클릭 리스터
//    val mainTopViewSearchOnclickListener = object : View.OnClickListener {
//        override fun onClick(v: View?) {
//            searchItemCount = filterList?.size?:0;
//            Log.d("searchItemCount 수 : ",""+searchItemCount)
//            TopView?.setHeight(SearchViewSize + searchItemCount * recyclerItemSize)
//            MainSearchListView?.setHeight(searchItemCount * recyclerItemSize)
//            MainViewChange?.setHeight(0)
//            MainTopDragView?.setHeight(0)
//        }
//    }

//    val mainTopViewSearchOnClose = object : SearchView.OnCloseListener {
//        override fun onClose(): Boolean {
//            Log.d("onClose", " 닫김 ");
//            TopView?.setHeight(BottomMin)
//            MainSearchListView?.setHeight(0)
//            MainViewChange?.setHeight(mainViewChangeSize)
//            MainTopDragView?.setHeight(0)
//            return false
//        }
//    }


    //검색리스트 리사이클뷰 어댑터터
//    inner class mainActivitySearchRecyclerViewAdapter() :
//        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//        var mainSearchItem: ArrayList<MainSearchItem> = arrayListOf()
//
//        init {
//            mainSearchItem.add(MainSearchItem("검색1"))
//            mainSearchItem.add(MainSearchItem("검색2"))
//            mainSearchItem.add(MainSearchItem("검색3"))
//            notifyDataSetChanged()
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            var view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.main_search_item, parent, false)
//            return CustomViewHolder(view)
//        }
//
//        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}
//
//        override fun getItemCount(): Int {
//            return mainSearchItem.size
//        }
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var view = holder.itemView
//            view.main_search_item_title.text = mainSearchItem[position].title
//            //아이템 크기 조정
//            view.setHeight(recyclerItemSize);
//        }
//    }



    inner class SearchAdapter(private val context: Context) :
        BaseAdapter() {

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View? {
            val view: View = LayoutInflater.from(context).inflate(R.layout.main_search_item, null)
            val text = view.findViewById<TextView>(R.id.main_search_item_title)
            val list = filterList?.get(position)
            view.setHeight(recyclerItemSize);
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
            return filterList?.size?:0
        }

        init {
            val inflate: LayoutInflater = LayoutInflater.from(context)
        }
    }

    val TextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //검색창에서 검색 글자 추출하기
            writedWord =  SearchView!!.text.toString()
            //추출한뒤 writedWord에 집어 넣어줘야함
            val text = writedWord?:""
            Log.d("Text",""+text)
            Log.d("originalList",""+originalList?.size)
            Log.d("filterList",""+ filterList?.size)
            search(text)
        }

        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }


    //드래그용 터치 이벤트 리스너
    val mainTopViewOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    }


    //드래그용 터치 이벤트 리스너
    val mainTopViewOnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                //창을 눌럿을떄
                MotionEvent.ACTION_DOWN -> {
                }
                //움직임이 감지 됫을때
                MotionEvent.ACTION_MOVE -> {
                    if (isTopViewOpen == false) {
                        //드래그 기능만 사용
                        //이동값 + 현재뷰 크기로 바뀌어야할 뷰 크기 계산
                        var h = event.getY().toInt().intTodP();
                        //뷰 크기 변화
                        if (h >= BottomMin && h <= BottomMax) {
                            v?.setHeight(h);
                        }
                    }
                }
                //손땟을때
                MotionEvent.ACTION_UP -> {
                    //창이 닫겨있을때
                    if ((v?.height?.intTodP() != BottomMin).and(isTopViewOpen == false)) {
                        //드래그로 이동한 경우   - 열림처리       이동할떄마다 크기가 변햇음으로 크기변화처리x
                        isTopViewOpen = true;
                    } else if ((v?.height?.intTodP() == BottomMin).and(isTopViewOpen == false)) {
                        //드래그로 이동햇다 닫은경우  - 닫은처리   해당부분은 없어도 되나 해당 이벤트가 있다는것을 명시적으로 표기하기위해 작성
                        isTopViewOpen = false;
                    } else if (isTopViewOpen == true) {//창이 열려있을때
                        v?.setHeight(BottomMin);
                        isTopViewOpen = false;
                    }
                }
            }
            return false;
        }
    }


    //View
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }


    //2020/9/17 최석우 int값 dp로 변환하는 함수
    public fun Int.dp(): Int { //함수 이름도 직관적으로 보이기 위해 dp()로 바꿨습니다.
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics)
            .toInt()
    }

    public fun Int.intTodP(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return this.div(density).toInt();
    }


    //2020/9/17 최석우 뷰 크기 변환함수
    fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value.dp();
            layoutParams = lp
        }
    }


    fun search(charText: String) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        filterList!!.clear()
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length == 0) {
            filterList!!.addAll(originalList!!)
        } else {
            // 리스트의 모든 데이터를 검색한다.
            for (i in originalList!!.indices) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (originalList!![i].toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    filterList!!.add(originalList!![i])
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        SearchViewAdapter!!.notifyDataSetChanged()
    }


}

