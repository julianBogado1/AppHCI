package hci.app.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hci.app.R

@Composable
fun DisplayImage() {
    val imageResId = R.drawable.ourlogo


    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
    )

}