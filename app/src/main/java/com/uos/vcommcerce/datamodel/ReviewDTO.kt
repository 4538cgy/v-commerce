package com.uos.vcommcerce.datamodel
//ProductDTO 통합 가능
data class ReviewDTO(

    //닉네임
    var nickName : String ? = null,
    //Firebase Uid
    var id : String ? = null,
    //리뷰 포스트 자체의 id
    var reviewId : String ? = null,
    //리뷰 내용
    var reviewExplain : String ? = null,
    //리뷰 사진 최대 N장의 리스트 - ArrayList
    var reviewImageUrlList : ArrayList<String> ? = null,
    //리뷰의 별점 1~5점
    var reviewPoint : Double ? = 0.0,
    //서버의 타임 MilliSecond로 저장
    var serverTimestamp : Long ? = 0,
    //표시해줄 현재 시간 ( String 값 )
    var timestamp : String ? = null
)