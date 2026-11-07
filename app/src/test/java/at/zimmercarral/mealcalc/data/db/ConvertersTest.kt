package at.zimmercarral.mealcalc.data.db

import org.junit.Test
import org.junit.Assert.*
import java.util.Date

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun fromTimestamp_nonNullValue_returnsCorrectDate() {
        val timestamp = System.currentTimeMillis()
        val expectedDate = Date(timestamp)
        assertEquals(expectedDate, converters.fromTimestamp(timestamp))
    }

    @Test
    fun fromTimestamp_nullValue_returnsNull() {
        assertNull(converters.fromTimestamp(null))
    }

    @Test
    fun dateToTimestamp_nonNullDate_returnsCorrectTimestamp() {
        val date = Date()
        val expectedTimestamp = date.time
        assertEquals(expectedTimestamp, converters.dateToTimestamp(date))
    }

    @Test
    fun dateToTimestamp_nullDate_returnsNull() {
        assertNull(converters.dateToTimestamp(null))
    }
}
