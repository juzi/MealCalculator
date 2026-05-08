package at.zimmercarral.mealcalc.data.db

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Date

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `null long becomes null date`() {
        assertNull(converters.fromTimestamp(null))
    }

    @Test
    fun `null date becomes null long`() {
        assertNull(converters.dateToTimestamp(null))
    }

    @Test
    fun `long round-trips through Date`() {
        val now = 1_700_000_000_000L
        val date = converters.fromTimestamp(now)!!
        assertEquals(now, date.time)
        assertEquals(now, converters.dateToTimestamp(date))
    }

    @Test
    fun `dateToTimestamp returns the underlying epoch millis`() {
        val date = Date(0)
        assertEquals(0L, converters.dateToTimestamp(date))
    }
}
