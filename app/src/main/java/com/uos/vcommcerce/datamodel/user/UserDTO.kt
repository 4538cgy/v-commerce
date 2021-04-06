package com.uos.vcommcerce.datamodel.user

data class UserDTO(

    //유저 uid
    var uid: String? = null,
    //유저 닉네임
    var userNickName: String? = null,
    //유저 ID
    var id: String? = null,
    //유저 password
    var password: String? = null,
    //유저 이름
    var userName: String? = null,
    //유저 핸드폰 번호
    var phoneNumber: String? = null,
    //회원 가입 시간
    var signUpTime: Long? = null,
    //성인인증 체크
    var adultVerify: Boolean? = null,
    var sellerVerify: Boolean? = null,
    //국내 주소
    var koreaAddress: MutableMap<String, KoreaAddress>? = null,
    //해외 주소
    var globalAddress: MutableMap<String, GlobalAddress>? = null,
    //이용약관 동의 여부
    var servicePolicyAcceptCheck: Boolean? = null,
    //개인정보처리 약관
    var personalInfoPolicyAcceptCheck: Boolean? = null,
    //수신 동의
    var alarmAcceptCheck: Boolean? = null

    ) {
    data class KoreaAddress(
        //배송지 별명
        var title: String? = null,
        //받는사람
        var name: String? = null,
        //받는 사람 연락처
        var phoneNumber: String,
        //우편 번호 + 배송 주소
        var address: String? = null,
        //상세주소
        var detailAddress: String? = null,
        //배송 방법 ( 문앞 , 경비실 등등 )
        var deliveryType: String?
    )

    data class GlobalAddress(
        //배송지 별명
        var title: String? = null,
        //받는사람
        var contactName: String? = null,
        //받는 국가 및 지역
        var countryRegion: String? = null,
        //도로명
        var streetAddress: String? = null,
        //상세 주소
        var streetDetailAddress: String? = null,
        //주/연방/지역/도시
        var region: String? = null,
        //도시 세부
        var regionDepartment: String? = null,
        //zip code
        var zipCode: String? = null,
        //국가 코드
        var nationCode: String? = null,
        //핸드폰 번호
        var phoneNumber: String? = null
    )
}