package com.uos.vcommcerce.util.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uos.vcommcerce.util.Event

typealias EventLiveData<T> = LiveData<Event<T>>

class MutableEventLiveData<T> : MutableLiveData<Event<T>>() {
    var event: T?
        get() = value?.peekContent()
        set(value) {
            if (value != null) {
                setValue(Event(value))
            }
        }

    fun postEvent(value: T?) {
        if (value != null) {
            postValue(Event(value))
        }
    }
}