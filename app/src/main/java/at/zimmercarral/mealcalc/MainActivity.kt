package at.zimmercarral.mealcalc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.sync.NetworkManager
import at.zimmercarral.mealcalc.data.sync.SynchronizationTask
import at.zimmercarral.mealcalc.edit.EditItemDialogViewModel
import at.zimmercarral.mealcalc.edit.EditItemLayout
import at.zimmercarral.mealcalc.edit.Focus
import at.zimmercarral.mealcalc.meal.MealLayout
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchLayout
import at.zimmercarral.mealcalc.search.SearchViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private val prefName = "prefs"
    private val stateKeyMealItems = "key_meal_items";
    private val networkManager = NetworkManager(this)
    private val synchronizationTask = SynchronizationTask()
    private var mealViewModel = MealViewModel(listOf())

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db by lazy {
            Room.databaseBuilder(
                this.applicationContext,
                FoodItemDatabase::class.java,
                "food-items.db"
            ).allowMainThreadQueries().build()
        }

        var savedMealItems = savedInstanceState?.getString(stateKeyMealItems)
        if (savedMealItems?.isNotBlank() == true) {
            mealViewModel.setItemsFromJson(savedMealItems)
        } else {
            val sharedPrefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)
            savedMealItems = sharedPrefs.getString(stateKeyMealItems, null)
            if (savedMealItems?.isNotBlank() == true) {
                mealViewModel.setItemsFromJson(savedMealItems)
            }
        }

        val editItemDialogViewModel = EditItemDialogViewModel(db.dao())
        val availableItems = db.dao().getAllItems()
        fixModificationDate(availableItems, db.dao())
        val searchViewModel = SearchViewModel(availableItems)

        if (networkManager.isInHomeNetwork()) {
            synchronizationTask.syncDatabases(db.dao(),
                onSyncCompleted = {
                    val syncedItems = db.dao().getAllItems()
                    fixModificationDate(syncedItems, db.dao())
                    searchViewModel.setItems(syncedItems)
                })
        }

        enableEdgeToEdge()
        setContent {
            AppTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = event.duration
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = MealScreen(null)) {
                        composable<MealScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(600)
                                )
                            }
                        ) {
                            MealLayout(navController, mealViewModel,
                                onEditItem = { index ->
                                    val item = mealViewModel.mealItems[index]
                                    editItemDialogViewModel.clear()
                                    editItemDialogViewModel.setIndex(index)
                                    editItemDialogViewModel.setItem(item)
                                    editItemDialogViewModel.setInUpdateMode(true)
                                    editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                                    navController.navigate(EditScreen(
                                        inUpdateMode = true,
                                        initialFocus = Focus.AMOUNT

                                    ))
                                },
                                onAddNewItem = {
                                    editItemDialogViewModel.clear()
                                    editItemDialogViewModel.setInUpdateMode(false)
                                    editItemDialogViewModel.setInitialFocus(Focus.NAME)
                                    navController.navigate(EditScreen(
                                        inUpdateMode = false,
                                        initialFocus = Focus.NAME
                                    ))
                                })
                        }
                        composable<EditScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(600)
                                )
                            }
                        ) {
                            EditItemLayout(navController, editItemDialogViewModel,
                                onAddOrUpdateItem = {
                                    mealViewModel.addOrUpdateItem(it)
                                    navController.navigate(MealScreen(it.uuid))
                                },
                                onDismiss = { navController.navigate(MealScreen(null)) })
                        }
                        composable<SearchScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Start,
                                    animationSpec = tween(400)
                                )
                            }
                        ) {
                            searchViewModel.setExpanded(true)
                            searchViewModel.setSearchQuery("")
                            SearchLayout(modifier = Modifier,
                                navController,
                                searchViewModel,
                                onEditItem = {
                                    editItemDialogViewModel.setItem(it)
                                    editItemDialogViewModel.setInUpdateMode(false)
                                    editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                                    navController.navigate(EditScreen(
                                        inUpdateMode = false,
                                        initialFocus = Focus.AMOUNT
                                    ))
                                },
                                onAddItem = {
                                    mealViewModel.addOrUpdateItem(it)
                                    navController.navigate(MealScreen(it.uuid))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fixModificationDate(
        items: List<FoodItem>,
        dao: FoodItemDao
    ) {
        items.forEach {
            if (it.lastModifiedAt == null) {
                it.lastModifiedAt = LocalDateTime.now()
                dao.updateOrInsert(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val json = mealViewModel.getItemsAsJson()
        outState.putString(stateKeyMealItems, json)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause();

        val sharedPrefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        val json = mealViewModel.getItemsAsJson()
        editor?.putString(stateKeyMealItems, json)
        editor?.apply()
    }
}

@Serializable
data class MealScreen(
    val addOrUpdateUuid: String?
)

@Serializable
data class EditScreen(
    val inUpdateMode: Boolean,
    val initialFocus: Focus
)

@Serializable
object SearchScreen


