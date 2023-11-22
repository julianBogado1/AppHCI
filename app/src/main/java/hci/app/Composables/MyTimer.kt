package hci.app.Composables

import android.util.Log
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
fun MyTimer(seconds: Int, onTimerTick: (Int) -> Unit, onTimerFinish:() ->Unit, inBreak:Boolean, inStop:Boolean, stopChange: (Boolean)->Unit, nextRemainingTime: Int) {
    if(seconds==0) {
        return
    }

    var progress by remember { mutableStateOf(1f) }
    var remainingTime by remember {mutableStateOf(seconds*1000L)}

    LaunchedEffect(key1 = remainingTime, key2=inBreak) {

        while (!inStop && !inBreak && remainingTime > 0) {
            delay(1000)
            remainingTime -= 1000
            progress = remainingTime.toFloat() / (seconds*1000L).toFloat()
            onTimerTick((remainingTime/1000).toInt())
        }

        if(!inBreak || (inStop && inBreak)) {
            remainingTime = nextRemainingTime.toLong()
            progress = 1f
            onTimerFinish()
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (inStop) {
            stopChange(false)
        /*
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
                //Text(text=String.format("%d",seconds))
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
