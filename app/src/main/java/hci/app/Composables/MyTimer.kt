package hci.app.Composables

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import kotlinx.coroutines.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment

@Composable
fun MyTimer(seconds: Int, onTimerTick: (Long) -> Unit, onTimerFinish:() ->Unit, inBreak:Boolean, inStop:Boolean, stopChange: (Boolean)->Unit) {
    val milis = remember { mutableStateOf(seconds * 1000L) }
    var progress by remember { mutableStateOf(1f) }
    var isTimerFinished by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(milis.value) }
    LaunchedEffect(key1 = milis.value, key2=inBreak) {
        while (!inStop && !inBreak && remainingTime > 0) {
            delay(1000)
            remainingTime -= 1000
            progress = remainingTime.toFloat() / milis.value.toFloat()
            onTimerTick(remainingTime)
        }
        if(!inBreak) {
            isTimerFinished = true
            onTimerFinish()
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isTimerFinished) {/*
            Text(
                text = "Time's up!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF000000)
            )*/
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = (String.format("%02d:%02d", (remainingTime / 60000), (remainingTime / 1000) % 60)),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF000000)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    color = Color(0xFF000000)
                )
            }
        }
    }
}
