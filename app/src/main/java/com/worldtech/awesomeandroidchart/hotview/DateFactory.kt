package com.worldtech.awesomeandroidchart.hotview

import java.util.Calendar

object DateFactory {
    @JvmStatic
    fun getDays(startDate: Calendar): List<Day> {
        val days: MutableList<Day> = ArrayList()
        val today = Calendar.getInstance()
        today.clear(Calendar.HOUR)
        today.clear(Calendar.MINUTE)
        today.clear(Calendar.SECOND)
        today.clear(Calendar.MILLISECOND)
        val timePadding = today.getTimeInMillis() - startDate.getTimeInMillis()
        val dayNum = (timePadding / 1000 / 60 / 60 / 24).toInt()
        var day: Day
        for (i in 0..dayNum) {
            day = Day()
            day.year = today[Calendar.YEAR]
            day.dayOfMonth = today[Calendar.DAY_OF_MONTH]
            day.dayOfWeek = getDayOfWeek(today[Calendar.DAY_OF_WEEK])
            day.month = today[Calendar.MONTH] + 1
            days.add(day)
            today.add(Calendar.DAY_OF_YEAR, -1)
        }
        return days
    }

    private fun getDayOfWeek(calendarDayOfWeek: Int): Int {
        return when (calendarDayOfWeek) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 0
        }
    }
}