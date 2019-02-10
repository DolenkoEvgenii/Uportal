package etu.uportal.utils.helpers

import java.text.SimpleDateFormat
import java.util.*

private enum class DateExpr {
    YEAR, MONTH, DAY,
    HOUR, MINUTE, SECOND,
    WEEK, DAY_YEAR, WEEK_YEAR,
    CONSTELLATION
}

fun Long.date(pattern: String = "yyyy-MM-dd HH:mm:ss"): String? = SimpleDateFormat(pattern, Locale.CHINA).format(this)

fun Long.year() = getData(this, DateExpr.YEAR)

fun Long.month() = getData(this, DateExpr.MONTH)

fun Long.day() = getData(this, DateExpr.DAY)

fun Long.week() = getData(this, DateExpr.WEEK)

fun Long.hour() = getData(this, DateExpr.HOUR)

fun Long.minute() = getData(this, DateExpr.MINUTE)

fun Long.second() = getData(this, DateExpr.SECOND)

fun Long.dayOfYear() = getData(this, DateExpr.DAY_YEAR)

fun Long.weekOfYear() = getData(this, DateExpr.WEEK_YEAR)

fun Long.constellation() = getData(this, DateExpr.CONSTELLATION)

fun Long.dateOnly(split: String = "-") = "${year()}$split${month()}$split${day()}"

fun Long.timeOnly(split: String = ":") = "${hour()}$split${minute()}$split${second()}"

fun Int.isLeapYear() = (this % 4 == 0) && (this % 100 != 0) || (this % 400 == 0)

private fun getData(timeMillis: Long, expr: DateExpr): String {
    val cal = Calendar.getInstance()
    cal.time = Date(timeMillis)
    return when (expr) {
        DateExpr.YEAR -> cal[Calendar.YEAR].toString()
        DateExpr.MONTH -> (cal[Calendar.MONTH] + 1).toString().prefix0()
        DateExpr.DAY -> cal[Calendar.DAY_OF_MONTH].toString().prefix0()
        DateExpr.WEEK -> {
            val week = arrayOf("Неизвестно", "Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
            week[cal.get(Calendar.DAY_OF_WEEK)]
        }
        DateExpr.HOUR -> cal[Calendar.HOUR_OF_DAY].toString().prefix0()
        DateExpr.MINUTE -> cal[Calendar.MINUTE].toString().prefix0()
        DateExpr.SECOND -> cal[Calendar.SECOND].toString().prefix0()
        DateExpr.DAY_YEAR -> cal[Calendar.DAY_OF_YEAR].toString()
        DateExpr.WEEK_YEAR -> cal[Calendar.WEEK_OF_YEAR].toString()
        DateExpr.CONSTELLATION -> getConstellations(timeMillis.month().toInt(), timeMillis.day().toInt())
    }
}

private fun getConstellations(month: Int, day: Int): String {
    val constellations = arrayOf(arrayOf("Козерог", "Водолей"), arrayOf("Водолей", "Рыбы"), arrayOf("Рыбы", "Овен"), arrayOf("Овен", "Телец"), arrayOf("Телец", "Близнецы"), arrayOf("Близнецы", "Рак"), arrayOf("Рак", "Лев"), arrayOf("Лев", "Дева"), arrayOf("Дева", "Весы"), arrayOf("Весы", "Скорпион"), arrayOf("Скорпион", "Стрелец"), arrayOf("Стрелец", "Козероr"))
    val dateSplit = intArrayOf(20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22)
    val split = dateSplit[month - 1]
    val constellation = constellations[month - 1]
    return if (day >= split) constellation[1] else constellation[0]
}

private fun String.prefix0() = if (length == 1) "0$this" else this