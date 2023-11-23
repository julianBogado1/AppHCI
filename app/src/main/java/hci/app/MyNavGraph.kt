package hci.app

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import hci.app.Composables.MyListScreen
import hci.app.Composables.MyRoutineDetailScreen
import hci.app.Composables.MyRutineExecScreen1
import hci.app.Composables.MySettingsScreen
import hci.app.ui.main.MainViewModel

@Composable
fun MyNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    val uri = "https://www.creatina.share.com"
    NavHost(
        navController = navController,
        startDestination = Screen.RoutinesScreen.route,
    ) {
        composable(Screen.RoutinesScreen.route) {
            MyListScreen(viewModel = viewModel) { route ->
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }
        }
        composable(Screen.SettingsScreen.route) {
            MySettingsScreen(viewModel)
        }
        composable("routine-exec1/{routineId}",
            arguments = listOf(
                navArgument("routineId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            MyRutineExecScreen1(
                navController = navController,
                routineId = navController.currentBackStackEntry?.arguments?.getInt("routineId")
                    ?: -1, viewModel = viewModel
            ) { route ->
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }
        }
        composable("routine-details/{routineId}",
            deepLinks = listOf( navDeepLink {
                uriPattern="$uri/rutinas?id={routineId}"
                action = Intent.ACTION_VIEW
            }
            ),arguments = listOf(
                navArgument("routineId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            MyRoutineDetailScreen(
                routineId = navController.currentBackStackEntry?.arguments?.getInt("routineId") ?: -1,
                viewModel = viewModel
            ) { route ->
                navController.navigate(route) {
                    launchSingleTop = true
                }
            }
        }
    }
}

fun navigateToHome(navController: NavHostController) {
    navController.navigate("routines") {
        launchSingleTop = true
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }
}
