package hci.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            RoutineDetailScreen(viewModel = viewModel, routineId = 14)
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(viewModel = viewModel)
        }
    }
}