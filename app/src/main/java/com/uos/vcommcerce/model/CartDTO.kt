package com.uos.vcommcerce.model



data class CartDTO(
    var productImage : String ? = null,
    var productOption : ArrayList<String> ? = null,
    var sellerName : String ? = null,
    var productCount : String ? = null,
    var transCost : String ? = null,
    var totalCost : String ? = null,
    var productAddOption : ArrayList<CartInnerDTO> ? = null

)
{
    data class CartInnerDTO(
        var option : String ? = null
    )
}