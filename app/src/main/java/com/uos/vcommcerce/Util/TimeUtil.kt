package com.uos.vcommcerce.Util

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    fun getTimeAll(): String {
        return SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분 ss초").format(Date(System.currentTimeMillis()))
            .toString()
    }

    fun getTimeYear(): String {
        return SimpleDateFormat("yyyy년").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeMonth(): String {
        return SimpleDateFormat("MM월").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeDay(): String {
        return SimpleDateFormat("dd일").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTime12HourClock(): String{
        return SimpleDateFormat("a").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeHour(): String {
        return SimpleDateFormat("hh시").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeMinute(): String{
        return SimpleDateFormat("mm분").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeSecond(): String{
        return SimpleDateFormat("ss초").format(Date(System.currentTimeMillis())).toString()
    }

    fun getTimeMilliSeconds(): Long{
        return System.currentTimeMillis()
    }
}