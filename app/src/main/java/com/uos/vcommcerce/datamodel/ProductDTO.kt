package com.uos.vcommcerce.datamodel

data class ProductDTO(

    //판매자이름
    var sellerName : String?,
    //판매자Uid
    var sellerUid : String?,
    //제품 이름
    var productName : String?,
    //판매자 주소
    var sellerAddress : String?,
    //제품 가격
    var productCost : String?,
    //제품 설명
    var productExplain : String?,
    //리뷰 평점 총합
    var totalRating : Long?,
    //평점 갯수
    var ratingCount : Long?,
    //업로드 시간
    var timestamp : Long?,
    //게시글 비활성화 체크
    var visible : Boolean?,
    //해당 글 본사람 목록
    var viewers : MutableMap<String,Boolean> = HashMap(),
    //해당 글 찜한 사람 목록
    var favorites : MutableMap<String,Boolean> = HashMap(),
    //매진, 일시품절 체크
    var selloutCheck : Boolean?,
    //카테고리 체크
    var category : String?,
    //비디오 리스트
    var videoList : ArrayList<String>?,
    //리뷰들
    var reviews : MutableMap<String,Review> = HashMap(),
    //해쉬태그 리스트
    var hashTagList : MutableMap<String,Boolean> = HashMap()
){
    data class Review(
        //해당 글에 리뷰 작성한 유저 id
        var userId : String?,
        //해당 글에 리뷰 작성한 유저 nickName
        var userNickName: String?,
        //리뷰 내용
        var commentExplain: String?,
        //리뷰 평점
        var rating : Long?,
        //작성한 시간
        var timstamp: Long?
    )
}