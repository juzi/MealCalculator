package net.zimmercarral.mealcalculator.data.db

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeConverterTest {

    private val converter = LocalDateTimeConverter()

    @Test
    fun `null string becomes null LocalDateTime`() {
        assertNull(converter.toLocalDateTime(null))
    }

    @Test
    fun `null LocalDateTime becomes null string`() {
        assertNull(converter.fromLocalDateTime(null))
    }

    @Test
    fun `LocalDateTime round-trips through ISO string`() {
        val source = LocalDateTime.of(2025, 4, 30, 12, 34, 56, 789_000_000)
        val text = converter.fromLocalDateTime(source)!!
        val parsed = converter.toLocalDateTime(text)
        assertEquals(source, parsed)
    }

    @Test
    fun `unparseable string returns null instead of throwing`() {
        assertNull(converter.toLocalDateTime("not a date"))
    }
}
