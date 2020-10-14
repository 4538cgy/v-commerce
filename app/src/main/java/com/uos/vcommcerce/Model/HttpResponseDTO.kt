package com.uos.vcommcerce.Model

data class HttpResponseDTO(
        var serverKey : String ? = "emptyServerKey",
        var url : String ? = "emptyUrl",
        var error : String ? = "emptyError",
        var msg : String ? = "emptyError"

) {
    data class UploadContentDTO(
        var token : String ? = "emptyToken",
        var uid : String ? = "emptyUid",
        var title : String ? = "emptyTitle",
        var body : String ? = "emptyBody",
        var category : ArrayList<String> ? = null
    )

    data class SearchAllListDTO(

        var pid : String ? = "emptyPid",
        var status : Int ? = null, //null = 없음 , 0  = 변환중 , 1 = 변환완료
        var thumbnail : String ? = "emptyThumbnail",
        var media : String ? = "emptyMedia"

    )

    data class DetailDTO(

        var pid :String ? = "emptyPid"

    )


}