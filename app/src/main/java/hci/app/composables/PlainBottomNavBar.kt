package hci.app.Composables
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


class PlainBottomNavBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PlainBottomAppBar() {
        Scaffold(
            bottomBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Small Top App Bar")
                    }
                )
            },
        ) { innerPadding ->
            Text(
                modifier = Modifier.padding(innerPadding),
                text = ""
            )

        }
    }

}
