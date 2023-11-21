package hci.app.util

import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hci.app.getTitle
import hci.app.ui.theme.GreenGhost
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import hci.app.Screen


@Composable
fun VerticalBar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.RoutinesScreen,
        Screen.SettingsScreen
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(72.dp)
            .background(GreenGhost),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToRoute(item.route) }
                    .background(if (currentRoute == item.route) Color.Gray else Color.Transparent),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = item.icon,
                        contentDescription = getTitle(item.titleId)
                    )
                    Text(
                        text=getTitle(item.titleId),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}