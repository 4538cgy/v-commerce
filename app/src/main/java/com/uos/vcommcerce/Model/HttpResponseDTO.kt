package com.uos.vcommcerce.Model

data class HttpResponseDTO(
        var serverKey : String ? = "emptyServerKey",
        var url : String ? = "emptyUrl"

) {
    data class UploadContentDTO(
        var token : String ? = null,
        var uid : String ? = null,
        var title : String ? = null,
        var body : String ? = null,
        var category : ArrayList<String> ? = null
    )

    data class SearchAllListDTO(

        var pid : String ? = null,
        var status : Int ? = null, //null = 없음 , 0  = 변환중 , 1 = 변환완료
        var thumbnail : String ? = null,
        var media : String ? = null

    )

    data class DetailDTO(

        var pid :String ? = null,
        var userInfo : Array<String> ? = arrayOf("empty","empty"),
        var title : String ? = null,
        var body : String ? = null,
        var thumbnail: String ? = null,
        var media : String ? = null,
        var eval : Array<String> ? = arrayOf("empty","empty")

    )

    data class errorDTO(
        var code : Int ? = 500,
        var msg : String ? = null
    )


}