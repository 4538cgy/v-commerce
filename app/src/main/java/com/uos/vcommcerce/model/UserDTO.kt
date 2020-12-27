package com.uos.vcommcerce.model

data class UserDTO(
    var uid: String? = null,
    var userNickName: String ? = null,
    var address: String? = null,
    var phoneNumber: String? = null,
    var serverTimestamp: Long ? = null,
    var timestamp: String ? = null
)