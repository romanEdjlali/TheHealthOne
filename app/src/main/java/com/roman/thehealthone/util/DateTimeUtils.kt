package com.roman.thehealthone.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun format(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}