package com.uos.vcommcerce.datamodel

import android.util.Log
import androidx.databinding.ObservableField
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class ProductDTO (
    //판매자이름
    var sellerName : String? = null,
    //판매자Uid
    var sellerUid : String? = null,
    //제품 이름
    var productName : String? = null,
    //판매자 주소
    var sellerAddress : String? = null,
    //가격 정보 맵
    var price : MutableMap<String,Long> = HashMap(),
    //제품 설명
    var productExplain : String? = null,
    //리뷰 평점 총합
    var totalRating : Long? = null,
    //평점 갯수
    var ratingCount : Long? = null,
    //업로드 시간
    var timestamp : Long? = null,
    //게시글 비활성화 체크
    var visible : Boolean? = null,
    //해당 글 본사람 목록
    var viewers : MutableMap<String,Boolean> = HashMap(),
    //해당 글 찜한 사람 목록
    var favorites : MutableMap<String,Boolean> = HashMap(),
    //매진, 일시품절 체크
    var selloutCheck : Boolean? = null,
    //카테고리 체크
    var category : String? = null,
    //비디오 리스트
    var videoList : ArrayList<String>? = null,
    //리뷰들
    var reviews : MutableMap<String,Review> = HashMap(),
    //해쉬태그 리스트
    var hashTagList : MutableMap<String,Boolean> = HashMap()
) {
    data class Review(
        //해당 글에 리뷰 작성한 유저 id
        var userId: String? = null,
        //해당 글에 리뷰 작성한 유저 nickName
        var userNickName: String? = null,
        //리뷰 내용
        var commentExplain: String? = null,
        //리뷰 평점
        var rating: Long? = null,
        //작성한 시간
        var timstamp: Long? = null
    )
}