package hci.app.Composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import hci.app.R

@Composable
fun ArmFlexIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_arm_flex),
        contentDescription = null // Provide a meaningful description if needed
    )
}

@Composable
fun ArmFlexOutlineIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_arm_flex_outline),
        contentDescription = null // Provide a meaningful description if needed
    )
}