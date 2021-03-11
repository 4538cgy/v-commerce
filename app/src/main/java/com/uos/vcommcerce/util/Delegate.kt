package com.uos.vcommcerce.util

class Delegate {
    interface Action {
        fun run()
    }

    interface Action1<T1> {
        fun run(t1: T1)
    }

    interface Action2<T1, T2> {
        fun run(t1: T1, t2: T2)
    }

    interface Action3<T1, T2, T3> {
        fun run(t1: T1, t2: T2, t3: T3)
    }

    interface Func<R> {
        fun call(): R
    }

    interface Func1<T1, R> {
        fun call(t: T1): R
    }

    interface Func2<T1, T2, R> {
        fun call(t1: T1, t2: T2): R
    }
}