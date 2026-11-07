package net.zimmercarral.mealcalculator.data.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudDone
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.CloudSync
import androidx.compose.material.icons.rounded.SyncProblem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.data.cloud.SyncState

@Composable
fun SyncStatusButton(
    syncState: SyncState,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var confirmDelete by remember { mutableStateOf(false) }
    val isSignedIn = syncState != SyncState.SIGNED_OUT
    val (icon, description) = when (syncState) {
        SyncState.SIGNED_OUT -> Icons.Rounded.CloudOff to R.string.sync_disabled
        SyncState.PENDING -> Icons.Rounded.CloudSync to R.string.sync_pending
        SyncState.SYNCED -> Icons.Rounded.CloudDone to R.string.sync_done
        SyncState.ERROR -> Icons.Rounded.SyncProblem to R.string.sync_error
    }
    Box(modifier = modifier) {
        IconButton(onClick = {
            if (isSignedIn) menuExpanded = true else onSignIn()
        }) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(description),
                tint = if (syncState == SyncState.ERROR) {
                    MaterialTheme.colorScheme.error
                } else {
                    LocalContentColor.current
                },
            )
        }
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.sign_out)) },
                onClick = {
                    menuExpanded = false
                    onSignOut()
                },
            )
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(R.string.delete_account),
                        color = MaterialTheme.colorScheme.error,
                    )
                },
                onClick = {
                    menuExpanded = false
                    confirmDelete = true
                },
            )
        }
    }
    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text(stringResource(R.string.delete_account_title)) },
            text = { Text(stringResource(R.string.delete_account_message)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmDelete = false
                    onDeleteAccount()
                }) {
                    Text(
                        stringResource(R.string.delete_account_confirm),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
        )
    }
}
