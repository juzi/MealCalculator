package net.zimmercarral.mealcalculator.help

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SwipeLeft
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.ui.AmountIcon
import net.zimmercarral.mealcalculator.ui.CarbsIcon
import net.zimmercarral.mealcalculator.ui.FatIcon
import net.zimmercarral.mealcalculator.ui.ProteinIcon
import net.zimmercarral.mealcalculator.ui.theme.AppTheme

/**
 * Full-screen walkthrough that explains how to create and add foods and what
 * the nutrient symbols mean. Shown on first start and from the help button.
 *
 * Drawn as a layer above the main screen rather than as a Dialog: a full-screen
 * Dialog window does not get the system bar insets, so its bottom buttons end
 * up behind the navigation bar. The surface blocks touches to the screen below.
 */
@Composable
fun HelpScreen(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    BackHandler(onBack = onDismiss)
    Surface(
        modifier = modifier.fillMaxSize(),
        color = colorScheme.surfaceContainer,
    ) {
        HelpContent(onDone = onDismiss)
    }
}

/** One line on a help page: an icon and the text explaining it. */
private class HelpEntry(
    val icon: ImageVector,
    @param:StringRes val text: Int,
    /** String resources inserted into [text], e.g. button labels. */
    val formatArgs: List<Int> = emptyList(),
    val iconSize: Dp = 28.dp,
    /** Draw the icon like the app's round + button. */
    val asButton: Boolean = false,
)

private class HelpPage(
    @param:StringRes val title: Int,
    val entries: List<HelpEntry>,
    @param:StringRes val footer: Int? = null,
)

// Composable because the custom nutrient icons are @Composable getters.
@Composable
private fun helpPages() = listOf(
    HelpPage(
        title = R.string.help_create_title,
        entries = listOf(
            HelpEntry(Icons.Rounded.Add, R.string.help_create_plus, asButton = true),
            HelpEntry(Icons.Rounded.Edit, R.string.help_create_values),
            HelpEntry(AmountIcon, R.string.help_create_amount, listOf(R.string.add_item), 30.dp),
        ),
    ),
    HelpPage(
        title = R.string.help_find_title,
        entries = listOf(
            HelpEntry(Icons.Rounded.Search, R.string.help_find_search),
            HelpEntry(Icons.Rounded.TouchApp, R.string.help_find_tap, listOf(R.string.add_item)),
            HelpEntry(Icons.Rounded.Add, R.string.help_find_plus),
        ),
    ),
    HelpPage(
        title = R.string.help_symbols_title,
        entries = listOf(
            // Same sizes as in the nutrient totals row, so the symbols look alike.
            HelpEntry(CarbsIcon, R.string.help_symbol_carbs, iconSize = 30.dp),
            HelpEntry(FatIcon, R.string.help_symbol_fat, iconSize = 24.dp),
            HelpEntry(ProteinIcon, R.string.help_symbol_protein, iconSize = 32.dp),
            HelpEntry(AmountIcon, R.string.help_symbol_amount, iconSize = 30.dp),
        ),
        footer = R.string.help_symbols_footer,
    ),
    HelpPage(
        title = R.string.help_meal_title,
        entries = listOf(
            HelpEntry(Icons.Rounded.TouchApp, R.string.help_meal_edit),
            HelpEntry(Icons.Rounded.SwipeLeft, R.string.help_meal_remove),
            HelpEntry(
                Icons.Rounded.Delete,
                R.string.help_meal_clear,
                listOf(R.string.clear_menu, R.string.undo),
            ),
            HelpEntry(Icons.Rounded.CloudOff, R.string.help_meal_sync),
            HelpEntry(Icons.AutoMirrored.Rounded.HelpOutline, R.string.help_meal_help),
        ),
    ),
)

@Composable
private fun HelpContent(onDone: () -> Unit, modifier: Modifier = Modifier) {
    val pages = helpPages()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.lastIndex

    Column(modifier = modifier.fillMaxSize().safeDrawingPadding()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { index ->
            HelpPageContent(pages[index])
        }
        PageIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isLastPage) {
                Spacer(Modifier)
            } else {
                TextButton(onClick = onDone) { Text(stringResource(R.string.help_skip)) }
            }
            Button(onClick = {
                if (isLastPage) {
                    onDone()
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            }) {
                Text(stringResource(if (isLastPage) R.string.help_done else R.string.help_next))
            }
        }
    }
}

@Composable
private fun HelpPageContent(page: HelpPage, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.headlineSmall,
            color = colorScheme.onSurface,
            modifier = Modifier.semantics { heading() },
        )
        page.entries.forEach { HelpEntryRow(it) }
        page.footer?.let {
            Text(
                text = stringResource(it),
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HelpEntryRow(entry: HelpEntry) {
    val args = entry.formatArgs.map { stringResource(it) }.toTypedArray()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .then(
                    if (entry.asButton) {
                        Modifier.clip(CircleShape).background(colorScheme.primary)
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            // Decorative: the text next to it says what the icon stands for.
            Icon(
                imageVector = entry.icon,
                contentDescription = null,
                tint = if (entry.asButton) colorScheme.onPrimary else colorScheme.primary,
                modifier = Modifier.size(entry.iconSize),
            )
        }
        Text(
            text = stringResource(entry.text, *args),
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onSurface,
        )
    }
}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    val description = stringResource(R.string.help_page_indicator, currentPage + 1, pageCount)
    Row(
        modifier = modifier.semantics(mergeDescendants = true) { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) colorScheme.primary else colorScheme.outlineVariant
                    )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HelpContentPreview() {
    AppTheme {
        Surface(color = colorScheme.surfaceContainer) {
            HelpContent(onDone = {})
        }
    }
}

@PreviewLightDark
@Composable
private fun HelpSymbolsPagePreview() {
    AppTheme {
        Surface(color = colorScheme.surfaceContainer) {
            HelpPageContent(helpPages()[2])
        }
    }
}
