package hci.app

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import hci.app.ui.theme.Green
import hci.app.ui.theme.GreenGhost
import hci.app.ui.theme.GreenLime

@Composable
fun BottomBar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val items = listOf(
        Screen.RoutinesScreen,
        Screen.SettingsScreen
    )

    NavigationBar(
        containerColor = GreenGhost,

    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = getTitle(item.titleId))},
                label = { Text(text = getTitle(item.titleId)) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = { onNavigateToRoute(item.route) },
            )
        }
    }
}