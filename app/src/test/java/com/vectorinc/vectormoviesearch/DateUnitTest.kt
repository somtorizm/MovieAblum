package com.vectorinc.vectormoviesearch

import com.vectorinc.vectormoviesearch.util.convertToDate
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DateUnitTest(
    private val date : String,
    private val expectedDate :  String,
    private val scenario : String
) {
    companion object{
        @JvmStatic
        @Parameterized.Parameters(name = "Date converters {2}")
        fun todos() = listOf(
            arrayOf("2022-07-06","JULY 2022","July Date Converter"),
            arrayOf("2022-06-06","JUNE 2022","June Date Converter"),
            arrayOf("2022-01-06","JANUARY 2022","June Date Converter"),
            arrayOf("2002-01-06","JANUARY 2002","June Date Converter Year 2002")
        )

    }
    @Test
    fun test_DateFormat(){
        val actualDate = convertToDate(date)
        assertEquals(expectedDate,actualDate)
    }

}