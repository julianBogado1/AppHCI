package hci.app.Composables

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun MySettingsScreen() {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletSettingsLayout()
    } else {
        PhoneSettingsLayout()
    }
}

@Composable
fun PhoneSettingsLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom=80.dp)
    ){

        var Language by remember { mutableStateOf("Español") }
        var isDropdownVisible by remember { mutableStateOf(false) }

        val icon = if (isDropdownVisible)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        var TextFieldSize by remember { mutableStateOf(Size.Zero) }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(Modifier.padding(20.dp)) {
                OutlinedTextField(
                    value = Language,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            TextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Idioma") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { isDropdownVisible = !isDropdownVisible })
                    }
                )

                DropdownMenu(
                    expanded = isDropdownVisible,
                    onDismissRequest = { isDropdownVisible = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { TextFieldSize.width.dp })
                ) {
                    DropdownMenuItem("Español") {
                        Language = "Español"
                        isDropdownVisible = false
                    }
                    DropdownMenuItem("Inglés") {
                        Language = "Inglés"
                        isDropdownVisible = false
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4))
        ) {
            Text(
                text = "Cerrar Sesión",
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun TabletSettingsLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(start=80.dp)
    ){

        var Language by remember { mutableStateOf("Español") }
        var isDropdownVisible by remember { mutableStateOf(false) }

        val icon = if (isDropdownVisible)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        var TextFieldSize by remember { mutableStateOf(Size.Zero) }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(Modifier.padding(20.dp)) {
                OutlinedTextField(
                    value = Language,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            TextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Idioma") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { isDropdownVisible = !isDropdownVisible })
                    }
                )

                DropdownMenu(
                    expanded = isDropdownVisible,
                    onDismissRequest = { isDropdownVisible = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { TextFieldSize.width.dp })
                ) {
                    DropdownMenuItem("Español") {
                        Language = "Español"
                        isDropdownVisible = false
                    }
                    DropdownMenuItem("Inglés") {
                        Language = "Inglés"
                        isDropdownVisible = false
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4))
        ) {
            Text(
                text = "Cerrar Sesión",
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
private fun DropdownMenuItem(text: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(8.dp)
    ) {
        Text(text = text,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun MySettingsScreenPreview() {
    MySettingsScreen()
}