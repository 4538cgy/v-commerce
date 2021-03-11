package com.uos.vcommcerce.datamodel

data class UserDTO(

    //유저 uid
    var uid: String?,
    //유저 닉네임
    var userNickName: String?,
    //유저 ID
    var id: String?,
    //유저 password
    var password: String?,
    //유저 이름
    var userName: String?,
    //유저 핸드폰 번호
    var phoneNumber: String?,
    //회원 가입 시간
    var signUpTime: Long?,
    //성인인증 체크
    var adultVerify: Boolean?,
    var sellerVerify: Boolean?,
    //국내 주소
    var koreaAddress: MutableMap<String, KoreaAddress>?,
    //해외 주소
    var globalAddress: MutableMap<String, GlobalAddress>?,
    //이용약관 동의 여부
    var servicePolicyAcceptCheck: Boolean?,
    //개인정보처리 약관
    var personalInfoPolicyAcceptCheck: Boolean?,
    //수신 동의
    var alarmAcceptCheck: Boolean?

    ) {
    data class KoreaAddress(
        //배송지 별명
        var title: String?,
        //받는사람
        var name: String?,
        //받는 사람 연락처
        var phoneNumber: String,
        //우편 번호 + 배송 주소
        var address: String?,
        //상세주소
        var detailAddress: String?,
        //배송 방법 ( 문앞 , 경비실 등등 )
        var deliveryType: String?
    )

    data class GlobalAddress(
        //배송지 별명
        var title: String?,
        //받는사람
        var contactName: String?,
        //받는 국가 및 지역
        var countryRegion: String?,
        //도로명
        var streetAddress: String?,
        //상세 주소
        var streetDetailAddress: String?,
        //주/연방/지역/도시
        var region: String?,
        //도시 세부
        var regionDepartment: String?,
        //zip code
        var zipCode: String?,
        //국가 코드
        var nationCode: String?,
        //핸드폰 번호
        var phoneNumber: String?
    )
}