package hci.app

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import hci.app.ui.main.MainViewModel

@Composable
fun MyNavGraph(navController: NavHostController, viewModel: MainViewModel) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(viewModel = viewModel)
        }
        composable(Screen.RoutinesScreen.route) {
            RoutinesScreen(viewModel = viewModel) { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(viewModel = viewModel)
        }
        composable("routine-details/{routineId}", deepLinks = listOf(
            navDeepLink {
                uriPattern="https://www.creatina.com/rutina-view/{routineId}"
                action= Intent.ACTION_VIEW
            }
        ),arguments = listOf(
            navArgument("routineId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )) {
            RoutineDetailScreen(
                routineId = navController.currentBackStackEntry?.arguments?.getInt("routineId") ?: -1,
                viewModel = viewModel
            )
        }

    }
}