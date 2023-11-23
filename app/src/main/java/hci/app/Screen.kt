package hci.app

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

sealed class Screen(val titleId: Int, val icon: ImageVector, val route: String) {
    object RoutinesScreen : Screen(R.string.routines, Icons.Filled.Home, "routines")
    object SettingsScreen : Screen(R.string.settings, Icons.Filled.Settings, "settings")
}

@Composable
fun getTitle(
    id: Int
): String {
    return stringResource(id)
}
