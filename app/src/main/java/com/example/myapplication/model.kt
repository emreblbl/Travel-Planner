package com.example.myapplication

import android.icu.text.SimpleDateFormat
import java.util.*

class Convert{
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }
}
val convert=Convert()

class Trip {
    var tripID: Int = 0
    var tripNAME: String = ""
    var destinations: String = ""
    var tripStartDate: Long = 0
    var tripEndDate: Long = 0
    var tripNote: String=""
    var categories: String=""
    var activities: String=""
    var cities: String=""
}

class Adding {
     var activity: String=""
    var trip: String=""
    var city: String=""
    fun setDefault(){
        trip=""
        city=""
        activity=""
    }
}
val adding=Adding()

