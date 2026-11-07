package at.zimmercarral.mealcalc.data.db

import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverterTest {

    private val converter = LocalDateTimeConverter()
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Test
    fun fromLocalDateTime_nonNullDateTime_returnsCorrectString() {
        // To ensure the test is stable, we remove nanoseconds which might not be in the formatted string
        val dateTime = LocalDateTime.now().withNano(0)
        val expectedString = dateTime.format(formatter)
        assertEquals(expectedString, converter.fromLocalDateTime(dateTime))
    }

    @Test
    fun fromLocalDateTime_nullDateTime_returnsNull() {
        assertNull(converter.fromLocalDateTime(null))
    }

    @Test
    fun toLocalDateTime_validString_returnsCorrectDateTime() {
        val dateTimeString = "2023-10-26T10:15:30"
        val expectedDateTime = LocalDateTime.parse(dateTimeString, formatter)
        assertEquals(expectedDateTime, converter.toLocalDateTime(dateTimeString))
    }

    @Test
    fun toLocalDateTime_validStringWithMilliseconds_returnsCorrectDateTime() {
        val dateTimeString = "2024-03-15T10:30:45.123"
        val expectedDateTime = LocalDateTime.parse(dateTimeString, formatter)
        assertEquals(expectedDateTime, converter.toLocalDateTime(dateTimeString))
    }


    @Test
    fun toLocalDateTime_invalidString_returnsNull() {
        val invalidString = "not-a-date-time"
        assertNull(converter.toLocalDateTime(invalidString))
    }

    @Test
    fun toLocalDateTime_emptyString_returnsNull() {
        val emptyString = ""
        assertNull(converter.toLocalDateTime(emptyString))
    }

    @Test
    fun toLocalDateTime_nullString_returnsNull() {
        assertNull(converter.toLocalDateTime(null))
    }
}
