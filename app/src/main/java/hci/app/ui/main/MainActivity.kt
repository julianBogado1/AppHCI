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
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hci.app.BottomBar
import hci.app.Composables.*
import hci.app.MyNavGraph
import hci.app.R
import hci.app.ui.theme.TPETheme
import hci.app.util.VerticalBar
import hci.app.util.getViewModelFactory
import java.util.Locale
import kotlin.random.Random
import hci.app.Composables.*
import kotlinx.coroutines.launch

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
            style = MaterialTheme.typography.titleLarge,
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
        if (viewModel.uiState.isAuthenticated) {
            Scaffold(
                snackbarHost = { appState.snackbarHostState },
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
        } else {
            MyLoginScreen(viewModel)
        }
    }
}


