package hci.app.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RoundedButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Filled")
    }
}