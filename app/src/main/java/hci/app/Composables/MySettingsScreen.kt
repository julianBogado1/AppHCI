package hci.app.Composables

import androidx.compose.foundation.clickable
import hci.app.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import hci.app.ui.main.ActionButton
import hci.app.ui.main.MainViewModel

@Composable
fun MySettingsScreen(viewModel: MainViewModel) {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletSettingsLayout(viewModel)
    } else {
        PhoneSettingsLayout(viewModel)
    }
}

@Composable
fun PhoneSettingsLayout(viewModel : MainViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(0.5f)) // This will push the button and text to the middle and bottom respectively

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
            onClick = { viewModel.logout() },
            fontWeight = FontWeight.Bold,
            resId = R.string.logout
        )

        Spacer(modifier = Modifier.weight(0.45f)) // This will create some space between the button and the text

        Text(
            text = stringResource(R.string.copyright),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 80.dp)
        )

        Spacer(modifier = Modifier.weight(0.1f)) // This will push the text a bit up from the bottom
    }
}

@Composable
fun TabletSettingsLayout(viewModel: MainViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(start = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f)) // This will push the button and text to the middle and bottom respectively

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
            onClick = { viewModel.logout() },
            fontWeight = FontWeight.Bold,
            resId = R.string.logout
        )

        Spacer(modifier = Modifier.weight(0.45f)) // This will create some space between the button and the text

        Text(
            text = stringResource(R.string.copyright),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 80.dp)
        )


    }
}

