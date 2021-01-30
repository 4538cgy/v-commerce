package com.uos.vcommcerce.model

import java.io.Serializable

//판매자 등록 데이터
// 현재 파일 등록 및
data class SellerDTO (
    var isPersonal : Boolean = false,
    var isCorporate : Boolean = false,
    var corporateNum : String = "",
    var corporateRepresentativeName : String = "",
    var corporateBusinessRegistraton : String = "",
    var channelName : String = "",
    var channelUrl : String = "",
    var channelExplain : String = "",
    var bank : String = "",
    var bankAccount : String = ""
) : Serializable