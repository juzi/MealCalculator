package net.zimmercarral.mealcalculator.data.db

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtil {
    const val ZEROS = "0000"

    private val DATE_TIME_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    fun toString(localDate: LocalDateTime?): String {
        return localDate?.let { DATE_TIME_FORMATTER.format(it) } ?: ""
    }

    fun toDate(dateStr: String?): LocalDate? {
        if (dateStr.isNullOrEmpty() || dateStr.startsWith(ZEROS)) {
            return null
        }
        return LocalDate.parse(dateStr, DATE_TIME_FORMATTER)
    }

    fun isAfter(dateStrA: String?, dateStrB: String?): Boolean {
        if (dateStrA == "null" || dateStrA.isNullOrEmpty() || dateStrA.startsWith(ZEROS)) {
            return false
        }
        if (dateStrB == "null" || dateStrB.isNullOrEmpty() || dateStrB.startsWith(ZEROS)) {
            return true
        }
        return toDate(dateStrA)!!.isAfter(toDate(dateStrB))
    }
}