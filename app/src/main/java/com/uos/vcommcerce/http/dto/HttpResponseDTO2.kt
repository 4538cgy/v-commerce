package com.uos.vcommcerce.http.dto

class HttpResponseDTO2(
    var dummy : String ? = null
) {
    data class userDTO(
        var unique_id: String? = null,            // token or email address
        var user_id: String? = null,              // unique id value
        var profile_image: String? = null,        // base64 image data
        var email: String? = null,                // email address
        var cell_phone_number: String? = null,    // none "-" phone number
        var day_of_birth: String? = null,         // "1999-01-01"
        var auth: String? = null                  //init google,kakao,apple,email select only one
    )
}