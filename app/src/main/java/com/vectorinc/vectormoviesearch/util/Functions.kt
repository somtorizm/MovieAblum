package com.vectorinc.vectormoviesearch.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Functions {
    fun convertToPercentage(voteAverage: Double): Float {
        return voteAverage.toFloat() / 10
    }

   public fun convertToDate(date: String): String {
        val date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return "${date.dayOfWeek}" + " " + "${date.month}" + " " + "${date.year}"
    }


}
