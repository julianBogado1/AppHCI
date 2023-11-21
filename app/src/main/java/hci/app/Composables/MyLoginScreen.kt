package hci.app.Composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import hci.app.ui.main.ActionButton
import hci.app.ui.main.MainViewModel
import hci.app.R


@Composable
fun MyLoginScreen(viewModel: MainViewModel) {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletLoginLayout(viewModel)
    } else {
        PhoneLoginLayout(viewModel)
    }
}
@Composable
fun PhoneLoginLayout(viewModel : MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top=16.dp,bottom=80.dp)
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ){
            DisplayImage()
        }

        Spacer(modifier = Modifier.height(60.dp))

        val username = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = username.value,
            onValueChange = {username.value = it},
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = false,
            label = {Text(stringResource(R.string.username))}
        )

        Spacer(modifier = Modifier.height(30.dp))

        val password = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {password.value = it},
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = false,
            label = {Text(stringResource(id = R.string.password))}
        )

        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            resId = R.string.login,                                         //todo cambio de idioma
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
            fontWeight = FontWeight.Bold,
            onClick = {viewModel.login(username.value.text, password.value.text)},
        )
        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
            fontWeight = FontWeight.Bold,
            resId = R.string.logout,
            onClick = {
                viewModel.getRoutines()
                if (viewModel.uiState.routines != null) {
                    var text = ""
                    Log.d("routines????idk man", viewModel.uiState.routines.toString())
                    for (routineContentIt in viewModel.uiState.routines!!.content) {
                        Log.d("routineContentIt", routineContentIt.toString())
                        text += (routineContentIt.name + " ")
                    }
                    Log.d("SARACATUNGA-TEXT", text)
                }
            }
        )
    }
}

@Composable
fun TabletLoginLayout(viewModel : MainViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .padding(start=80.dp)
    ){
        Column( modifier = Modifier
            .fillMaxHeight()
            .padding(40.dp)
        ){
            Row(
            ){
                DisplayImage()
            }
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
        ){
            val username = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = username.value,
                onValueChange = {username.value = it},
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = false,
                label = {Text(stringResource(R.string.username))}
            )

            Spacer(modifier = Modifier.height(15.dp))

            val password = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it},
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = false,
                label = {Text(stringResource(id = R.string.password))}
            )

            ActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                resId = R.string.login,                                         //todo cambio de idioma
                colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
                fontWeight = FontWeight.Bold,
                onClick = {viewModel.login(username.value.text, password.value.text)},
            )
            ActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF73C7A4)),
                fontWeight = FontWeight.Bold,
                resId = R.string.logout,
                onClick = {
                    viewModel.getRoutines()
                    if (viewModel.uiState.routines != null) {
                        var text = ""
                        Log.d("routines????idk man", viewModel.uiState.routines.toString())
                        for (routineContentIt in viewModel.uiState.routines!!.content) {
                            Log.d("routineContentIt", routineContentIt.toString())
                            text += (routineContentIt.name + " ")
                        }
                        Log.d("SARACATUNGA-TEXT", text)
                    }
                }
            )
        }
    }
}
