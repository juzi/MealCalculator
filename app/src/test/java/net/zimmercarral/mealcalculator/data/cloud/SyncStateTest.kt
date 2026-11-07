package net.zimmercarral.mealcalculator.data.cloud

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SyncStateTest {

    @Test
    fun `fromSnapshot is SYNCED when nothing is pending and data comes from the server`() {
        assertEquals(SyncState.SYNCED, SyncState.fromSnapshot(hasPendingWrites = false, isFromCache = false))
    }

    @Test
    fun `fromSnapshot is PENDING while local writes are not acknowledged`() {
        assertEquals(SyncState.PENDING, SyncState.fromSnapshot(hasPendingWrites = true, isFromCache = false))
    }

    @Test
    fun `fromSnapshot is PENDING when the data comes from the cache`() {
        assertEquals(SyncState.PENDING, SyncState.fromSnapshot(hasPendingWrites = false, isFromCache = true))
        assertEquals(SyncState.PENDING, SyncState.fromSnapshot(hasPendingWrites = true, isFromCache = true))
    }

    @Test
    fun `hideBriefPending drops a PENDING that is resolved within the delay`() = runTest {
        val states = flow {
            emit(SyncState.SYNCED)
            delay(100)
            emit(SyncState.PENDING)
            delay(PENDING_DISPLAY_DELAY_MS - 1)
            emit(SyncState.SYNCED)
        }.hideBriefPending().toList()

        assertEquals(listOf(SyncState.SYNCED, SyncState.SYNCED), states)
    }

    @Test
    fun `hideBriefPending shows a PENDING that lasts longer than the delay`() = runTest {
        val states = flow {
            emit(SyncState.SYNCED)
            delay(100)
            emit(SyncState.PENDING)
            delay(PENDING_DISPLAY_DELAY_MS + 1)
            emit(SyncState.SYNCED)
        }.hideBriefPending().toList()

        assertEquals(listOf(SyncState.SYNCED, SyncState.PENDING, SyncState.SYNCED), states)
    }

    @Test
    fun `hideBriefPending passes ERROR on immediately and drops the PENDING before it`() = runTest {
        val states = flow {
            emit(SyncState.PENDING)
            delay(10)
            emit(SyncState.ERROR)
        }.hideBriefPending().toList()

        assertEquals(listOf(SyncState.ERROR), states)
    }
}
