package com.uos.vcommcerce.datamodel

import androidx.databinding.ObservableField

//메인페이져 옵저빙용 클래스
class ObservableProductDTO( product : ProductDTO){
    var sellerNickName : ObservableField<String> = ObservableField("sellerNickName")
    var productExplain : ObservableField<String> = ObservableField("productExplain")
    var address : ObservableField<String> = ObservableField("address")
    var cost : ObservableField<String> = ObservableField("cost")
    var productTile : ObservableField<String> = ObservableField("productTile")
    var uid : ObservableField<String> = ObservableField("uid")
    var sellerProfileImg : ObservableField<String> = ObservableField("sellerProfileImg")

    fun set(product: ProductDTO){
        if(product.sellerNickName!=null){ sellerNickName.set(product.sellerNickName!!)}
        if(product.productExplain!=null){ productExplain.set(product.productExplain!!)}
        if(product.address!=null){ address.set(product.address!!)}
        if(product.cost!=null){ cost.set(product.cost!!)}
        if(product.productTile!=null){ productTile.set(product.productTile!!)}
        if(product.uid!=null){ uid.set(product.uid!!)}
        if(product.sellerProfileImg!=null){ sellerProfileImg.set(product.sellerProfileImg!!)}
    }

    init {
        set(product)
    }
}