package hci.app.ui.main

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import hci.app.MyApplication
import hci.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyAppState(
    val scope : CoroutineScope,
    val snackbarHostState: SnackbarHostState
) {
    fun showSnackbar(message: String, actionPerformed: ()->Unit, dismissed:()->Unit) {
        scope.launch {
            val actionLabel = MyApplication.instance.getString(R.string.dismiss)
            val result = snackbarHostState.showSnackbar(message,actionLabel = actionLabel)
            when(result){
                SnackbarResult.ActionPerformed -> actionPerformed()
                SnackbarResult.Dismissed -> dismissed()
            }
        }
    }
}

@Composable
fun rememberMyAppState(scope : CoroutineScope= rememberCoroutineScope(),
                       snackbarHostState: SnackbarHostState=remember{SnackbarHostState()})
                        =remember(scope, snackbarHostState){
    MyAppState(scope, snackbarHostState)
}