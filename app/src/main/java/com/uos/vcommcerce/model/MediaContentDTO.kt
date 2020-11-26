package com.uos.vcommcerce.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class MediaContentDTO(url : String,title :String ,  content:String) : BaseObservable() {

    var url : ObservableField<String> = ObservableField(url)
    var title: ObservableField<String> = ObservableField(title)
    var content: ObservableField<String> = ObservableField(content)

    fun set(url :String ,title :String ,  content:String){
        this.url.set(url)
        this.title.set(title)
        this.content.set(content)
    }

    fun set(MediaContentDTO : MediaContentDTO){
        this.url.set(MediaContentDTO.url.get())
        this.title.set(MediaContentDTO.title.get())
        this.content.set(MediaContentDTO.content.get())
    }
}