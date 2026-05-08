package at.zimmercarral.mealcalc.data.db

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DateUtilTest {

    @Test
    fun `toString returns empty for null`() {
        assertEquals("", DateUtil.toString(null))
    }

    @Test
    fun `toString and toDate round-trip the date portion`() {
        val source = LocalDateTime.of(2025, 4, 30, 12, 34, 56, 789_000_000)
        val text = DateUtil.toString(source)
        assertEquals(LocalDate.of(2025, 4, 30), DateUtil.toDate(text))
    }

    @Test
    fun `toDate returns null for null, empty, or zero-prefixed strings`() {
        assertNull(DateUtil.toDate(null))
        assertNull(DateUtil.toDate(""))
        assertNull(DateUtil.toDate("0000-01-01T00:00:00.000Z"))
    }

    @Test
    fun `isAfter is false when first argument is null or sentinel`() {
        assertFalse(DateUtil.isAfter(null, "2025-04-30T00:00:00.000Z"))
        assertFalse(DateUtil.isAfter("", "2025-04-30T00:00:00.000Z"))
        assertFalse(DateUtil.isAfter("null", "2025-04-30T00:00:00.000Z"))
        assertFalse(DateUtil.isAfter("0000-01-01T00:00:00.000Z", "2025-04-30T00:00:00.000Z"))
    }

    @Test
    fun `isAfter is true when only the second argument is missing`() {
        val a = "2025-04-30T00:00:00.000Z"
        assertTrue(DateUtil.isAfter(a, null))
        assertTrue(DateUtil.isAfter(a, ""))
        assertTrue(DateUtil.isAfter(a, "null"))
        assertTrue(DateUtil.isAfter(a, "0000-01-01T00:00:00.000Z"))
    }

    @Test
    fun `isAfter compares calendar dates`() {
        val later = "2025-04-30T00:00:00.000Z"
        val earlier = "2025-04-29T23:59:59.999Z"
        assertTrue(DateUtil.isAfter(later, earlier))
        assertFalse(DateUtil.isAfter(earlier, later))
    }

    @Test
    fun `isAfter ignores the time portion of the same calendar day`() {
        val morning = "2025-04-30T01:00:00.000Z"
        val evening = "2025-04-30T23:00:00.000Z"
        assertFalse(DateUtil.isAfter(evening, morning))
        assertFalse(DateUtil.isAfter(morning, evening))
    }
}
