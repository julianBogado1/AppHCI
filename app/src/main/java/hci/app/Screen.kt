package hci.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector, val route: String) {
    object HomeScreen: Screen("Home", Icons.Filled.Home, "home")
    object RoutinesScreen: Screen("Routines", Icons.Filled.PlayArrow, "routines")
    object SettingsScreen: Screen("Settings", Icons.Filled.Settings, "settings")
}