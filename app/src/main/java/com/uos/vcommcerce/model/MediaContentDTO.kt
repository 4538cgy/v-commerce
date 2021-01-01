package com.uos.vcommcerce.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class MediaContentDTO(url :String="url" ,title :String="제목" ,content:String="내용",nickname:String="이름",price:String="가격",address:String="주소"){
    var url : ObservableField<String> = ObservableField(url)
    var nickname: ObservableField<String> = ObservableField(nickname)
    var title: ObservableField<String> = ObservableField(title)
    var price: ObservableField<String> = ObservableField(price)
    var address: ObservableField<String> = ObservableField(address)
    var content: ObservableField<String> = ObservableField(content)


    fun set(url :String="url" ,title :String="제목" ,content:String="내용",nickname:String="이름",price:String="가격",address:String="주소"){
        this.url.set(url)
        this.title.set(title)
        this.content.set(content)
        this.nickname.set(nickname)
        this.price.set(price)
        this.address.set(address)
    }

    fun set(MediaContentDTO : MediaContentDTO){
        this.url.set(MediaContentDTO.url.get())
        this.title.set(MediaContentDTO.title.get())
        this.content.set(MediaContentDTO.content.get())
        this.nickname.set(MediaContentDTO.nickname.get())
        this.price.set(MediaContentDTO.price.get())
        this.address.set(MediaContentDTO.address.get())
    }
}