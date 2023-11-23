package hci.app.ui.main

import android.R.attr.label
import android.R.attr.text
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hci.app.BottomBar
import hci.app.Composables.*
import hci.app.MyNavGraph
import hci.app.R
import hci.app.ui.theme.TPETheme
import hci.app.util.VerticalBar
import hci.app.util.getViewModelFactory


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TPETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

fun copyLinkToClipboard(link: String, context: Context) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Link", link)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context, R.string.copiedLink, Toast.LENGTH_SHORT).show()
}

@Composable
fun ActionButton(
    @StringRes resId: Int,
    enabled: Boolean = true,
    modifier : Modifier,
    colors : ButtonColors = ButtonDefaults.buttonColors(),
    fontWeight: FontWeight,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(resId),
            fontWeight = fontWeight,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appState = rememberMyAppState() //todo TIRAR ERRORES AWEONAO

    if(viewModel.uiState.isAuthenticated) {
        Scaffold(
            snackbarHost = {appState.snackbarHostState},
            bottomBar = {
                if (LocalConfiguration.current.screenWidthDp >= 600) {
                    // Display bottom navigation as a column on the left
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp) // Set the desired width for the left navigation bar
                    ) {
                        VerticalBar(
                            currentRoute = currentRoute
                        ) { route ->
                            navController.navigate(route) {
                                navController.popBackStack(
                                    navController.graph.findStartDestination().id,
                                    false
                                )
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                } else {
                    BottomBar(
                        currentRoute = currentRoute
                    ) { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        ) {
            MyNavGraph(navController = navController, viewModel = viewModel)
        }
    }
    else{
        MyLoginScreen(viewModel)
    }
}

data class Rutina(
    val name: String,
    val description: String,
    val rating: Int,
    val duration: Int,
    val dUnit: String,
    val cicles: List<Cicle>,
    val points: Double,
    val date: String,
    val category: String
)

data class Cicle(
    val name: String,
    val number: Int,
    val rep: Int,
    val ejs: List<Ejercicio>
)

data class Ejercicio(
    val name: String,
    val description: String,
    val series: Int,
    val duration: Int,
    val dUnit: String)

/*if(!viewModel.uiState.isLoading && viewModel.uiState.isAuthenticated) {
        var routines = viewModel.getRoutines()
        text = ""
        for (routine in routines) {
            for (routineContentIt in routine.content) {
                text += (routineContentIt.name + " ")
            }
        }
        Log.d("SARACATUNGA-TEXT", text)
    }
    /*if(!viewModel.uiState.isLoading) {
        Log.d("UISTATE_AUTH", uiState.isAuthenticated.toString())
        viewModel.printAuth()
    }
    if(uiState.hasError){
        appState.showSnackbar(uiState.message, {viewModel.dismissMessage()}, {viewModel.dismissMessage()})
    }*/

    /*#######cuerpo de MainScreen######*/
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
            if (!uiState.isAuthenticated) {
                ActionButton(
                    resId = R.string.login,
                    onClick = {
                        viewModel.login("johndoe", "1234567890")
                    })

            } else {
                ActionButton(
                    resId = R.string.logout,
                    onClick = {
                        viewModel.logout()
                    })
                }
            if (uiState.error != null) {
                Text(
                    text = "${uiState.error.code} - ${uiState.error.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }*/

