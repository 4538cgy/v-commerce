package com.uos.vcommcerce.Util

import android.content.res.Resources
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.Model.MainSearchItem
import com.uos.vcommcerce.R
import com.uos.vcommcerce.isTopViewOpen
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_search_item.view.*

class MainTopSlideDown : View.OnClickListener,View.OnTouchListener {

    val BottomMin: Int = 100;
    val BottomMax: Int = 300;
    val recyclerItemSize: Int = 30;
    val SearchViewSize: Int = 40;

    companion object {
        var instance = MainTopSlideDown()
        var  TopView : View? = null;
        var  MainSearchListView : View? = null;
        var  MainViewChange : View? = null;
        var  MainTopDragView : View? = null;
        //현재 상태 확인
        var state = 0;
        var searchItemCount = 0;
    }


    //해당클래스에 필요한 뷰를 main에서 받아옴
    fun setTopView(topView : View,mainSearchListView : View,mainViewChange : View,mainTopDragView : View){
        TopView = topView;
        MainSearchListView = mainSearchListView;
        MainViewChange = mainViewChange;
        MainTopDragView = mainTopDragView;
    }


    //검색뷰 온클릭 리스터
    val mainTopViewSearchOnclickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            TopView?.setHeight(SearchViewSize + searchItemCount*recyclerItemSize)
            MainSearchListView?.setHeight(90)
            MainViewChange?.setHeight(0)
            MainTopDragView?.setHeight(0)
        }
    }

    //검색리스트 리사이클뷰 어댑터터
   inner class mainActivitySearchRecyclerViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var mainSearchItem: ArrayList<MainSearchItem> = arrayListOf()

        init {
            mainSearchItem.add(MainSearchItem("검색1"))
            mainSearchItem.add(MainSearchItem("검색2"))
            mainSearchItem.add(MainSearchItem("검색3"))
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.main_search_item, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {}

        override fun getItemCount(): Int {
            return mainSearchItem.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var view = holder.itemView
            view.main_search_item_title.text = mainSearchItem[position].title
            //아이템 크기 조정
            searchItemCount = getItemCount();
            view.setHeight(recyclerItemSize);
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

}

