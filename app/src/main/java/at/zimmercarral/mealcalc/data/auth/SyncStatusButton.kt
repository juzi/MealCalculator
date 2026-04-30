package at.zimmercarral.mealcalc.data.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import at.zimmercarral.mealcalc.R

@Composable
fun SyncStatusButton(
    isSignedIn: Boolean,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = {
            if (isSignedIn) menuExpanded = true else onSignIn()
        }) {
            Icon(
                imageVector = if (isSignedIn) Icons.Rounded.Cloud else Icons.Rounded.CloudOff,
                contentDescription = stringResource(
                    if (isSignedIn) R.string.sync_enabled else R.string.sync_disabled
                ),
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
        }
    }
}
