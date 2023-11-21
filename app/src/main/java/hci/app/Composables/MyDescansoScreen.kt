package hci.app.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MyDescansoScreen() {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletDescansoLayout()
    } else {
        PhoneDescansoLayout()
    }
}

@Composable
fun PhoneDescansoLayout(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF6EB5E9))
    ) {
        Column(
            modifier = Modifier
                .padding(top=80.dp,bottom=60.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(text="Descanso",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text="Tomate un respiro",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
            ) {
                Text(
                    text = "Renaudar",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TabletDescansoLayout(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF6EB5E9))
    ) {
        Column(
            modifier = Modifier
                .padding(top=40.dp,bottom=60.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(text="Descanso",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text="Tomate un respiro",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
            ) {
                Text(
                    text = "Renaudar",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


