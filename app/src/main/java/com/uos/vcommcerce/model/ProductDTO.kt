package com.uos.vcommcerce.model

import android.net.Uri

data class ProductDTO(
    var uid: String? = null,
    var videoUri : String ? = null,
    var sellerNickName : String ? = null,
    var productExplain : String ? = null,
    var address : String ? = null,
    var cost : String ? = null,
    var productTile : String ? = null,
    var serverTimestamp : Long ? = null,
    var timestamp : String ? = null,
    var userEmail : String ? = null
)