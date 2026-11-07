package net.zimmercarral.mealcalculator.data.cloud

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce

/** The cloud-sync status shown by the sync icon. */
enum class SyncState {
    /** No user is signed in, so nothing is synced. */
    SIGNED_OUT,

    /**
     * Signed in, but local changes have not reached the server yet, or the app
     * has no connection to the server (offline or still connecting).
     */
    PENDING,

    /** All local changes are on the server and the local data is up to date. */
    SYNCED,

    /** Sync stopped: the snapshot listener failed or the server rejected a write. */
    ERROR;

    companion object {
        /** Maps the metadata of a Firestore snapshot to a state. */
        fun fromSnapshot(hasPendingWrites: Boolean, isFromCache: Boolean): SyncState =
            if (hasPendingWrites || isFromCache) PENDING else SYNCED
    }
}

/** How long [SyncState.PENDING] has to last before it is shown. */
const val PENDING_DISPLAY_DELAY_MS = 1_000L

/**
 * Drops [SyncState.PENDING] states that last less than [delayMs]. Every edit is
 * pending only until the server acknowledges it, which usually takes a fraction
 * of a second, so without this the icon would flicker on each change.
 * All other states are passed on immediately.
 */
@OptIn(FlowPreview::class)
fun Flow<SyncState>.hideBriefPending(delayMs: Long = PENDING_DISPLAY_DELAY_MS): Flow<SyncState> =
    debounce { if (it == SyncState.PENDING) delayMs else 0L }
