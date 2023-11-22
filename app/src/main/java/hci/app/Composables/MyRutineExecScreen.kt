package hci.app.Composables

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import hci.app.R
import hci.app.ui.main.MainViewModel
import hci.app.ui.main.Rutina


@Composable
fun MyRutineExecScreen1(rutina: Rutina, viewModel: MainViewModel) {
    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineExec1Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    } else {
        PhoneRutineExec1Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    }
}

@Composable
fun MyRutineExecScreen2(rutina: Rutina, viewModel: MainViewModel) {
    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineExec2Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    } else {
        PhoneRutineExec2Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    }
}

@Composable
fun PhoneRutineExec1Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 40.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                }
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color(0xFF000000)
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${rutina.cicles[cicleIndex].name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                    val series = rutina.cicles[cicleIndex].ejs[ejIndex].series


                    val displayText = if (duration == 0 || series == 0) {
                        if (duration == 0) {
                            "${series} series"
                        } else {
                            "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"
                        }
                    } else {
                        "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                    }

                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${rutina.cicles[cicleIndex].ejs[ejIndex].description}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            item {if(!(timeRemainingSec>0))timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)
                //TODO: esto se rompe porque no esta circularizado!!!
                changeTimeCountdown(rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration*60*1000)
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                ){
                    if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                        MyTimer(
                            seconds = timeRemainingSec,
                            onTimerFinish = {
                                onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                if (ejIndex == 0){
                                    onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                }
                                //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                stopChange(false)
                                breakChange(false)
                                //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                            },
                            onTimerTick = timeRemainingSecChange,
                            inBreak = inBreak,
                            inStop = inStop,
                            stopChange = stopChange,
                            nextRemainingTime = timeCountdown
                        )
                    }
                }

            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            onClick = {
                                if (rutina.cicles[cicleIndex].ejs[ejIndex].duration != 0) {
                                    stopChange(true)
                                    breakChange(true)
                                } else {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                }
                                /*saracatunga*/
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                        ) {
                            Text(
                                text = stringResource(id = R.string.next),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (rutina.cicles[cicleIndex].ejs[ejIndex].duration != 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {
                                    breakChange(!inBreak)
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.rest),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TabletRutineExec1Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ){
                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].name}",
                            style = MaterialTheme.typography.titleLarge)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(30.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold)

                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                        val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                        val displayText = if (duration == 0 || series == 0) {
                            if(duration==0){ "${series} series"}
                            else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                        } else {
                            "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].description}",
                            style = MaterialTheme.typography.headlineSmall)
                    }
                }

            }

            Box(modifier = Modifier
                .padding(top=40.dp,bottom=40.dp)
            ){
                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                    color=(Color(0xFF000000)))
            }

            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = 20.dp, bottom = 20.dp)
            ){
                item{

                    /*###########EMPIEZA EL TAIMER###########*/
                    if(!(timeRemainingSec>0))timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)
                    //TODO: esto se rompe porque no esta circularizado!!!
                    changeTimeCountdown(rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration*60*1000)
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                    ){
                        if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                            MyTimer(
                                seconds = timeRemainingSec,
                                onTimerFinish = {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if (ejIndex == 0){
                                        onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                    //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                    stopChange(false)
                                    breakChange(false)
                                    //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                },
                                onTimerTick = timeRemainingSecChange,
                                inBreak = inBreak,
                                inStop = inStop,
                                stopChange = stopChange,
                                nextRemainingTime = timeCountdown
                            )
                        }
                    }
                }

                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {
                                    if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                                        stopChange(true)
                                        breakChange(true)
                                    }
                                    else{
                                        onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                        if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                            ) {
                                Text(
                                    text = stringResource(id =R.string.next),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if(timeRemainingSec!=0){
                            Row(modifier = Modifier
                                .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                    onClick = {
                                        breakChange(!inBreak)
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                                ) {
                                    Text(
                                        text = stringResource(R.string.rest),  //todo traducir al espanolo
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                    }
                }

            }
        }


    }
}

@Composable
fun PhoneRutineExec2Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 20.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                }
            }

            item{
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                    color= Color(0xFF000000))

                Spacer(modifier = Modifier.height(10.dp))
            }

            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.cicles[cicleIndex].name}",
                        style = MaterialTheme.typography.titleLarge)
                }
            }

            item{
                Spacer(modifier = Modifier.height(30.dp))
            }

            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold)

                }
            }

            item{
                Spacer(modifier = Modifier.height(10.dp))
            }

            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                    val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                    val displayText = if (duration == 0 || series == 0) {
                        if(duration==0){ "${series} series"}
                        else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                    } else {
                        "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                    }

                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item{if(!(timeRemainingSec>0))timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)
                //TODO: esto se rompe porque no esta circularizado!!!
                changeTimeCountdown(rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration*60*1000)
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                ){
                    if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                        MyTimer(
                            seconds = timeRemainingSec,
                            onTimerFinish = {
                                onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                if (ejIndex == 0){
                                    onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                }
                                //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                stopChange(false)
                                breakChange(false)
                                //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                            },
                            onTimerTick = timeRemainingSecChange,
                            inBreak = inBreak,
                            inStop = inStop,
                            stopChange = stopChange,
                            nextRemainingTime = timeCountdown
                        )
                    }
                }
            }

            item{
                Column(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            onClick = {

                                if (rutina.cicles[cicleIndex].ejs[ejIndex].duration != 0) {
                                    stopChange(true)
                                    breakChange(true)
                                } else {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                }
                                /*saracatunga*/

                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                        ) {
                            Text(
                                text = stringResource(R.string.next),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            onClick = {breakChange(!inBreak)},
                            colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                        ) {
                            Text(
                                text = stringResource(R.string.rest),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text= stringResource(id =R.string.nextInCycle),
                            style = MaterialTheme.typography.titleMedium)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))

                    rutina.cicles[cicleIndex].ejs.drop(ejIndex+1).forEach{ ejercicio ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .background(Color(0xFFBEBEBE))
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Box {
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${ejercicio.name}",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Text(
                                            text = "${ejercicio.description}",
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun TabletRutineExec2Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ) {
                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].name}",
                            style = MaterialTheme.typography.titleLarge)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(30.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold)

                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                        val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                        val displayText = if (duration == 0 || series == 0) {
                            if(duration==0){ "${series} series"}
                            else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                        } else {
                            "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item{
                    if(!(timeRemainingSec>0))timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)
                    //TODO: esto se rompe porque no esta circularizado!!!
                    changeTimeCountdown(rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration*60*1000)
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                    ){
                        if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                            MyTimer(
                                seconds = timeRemainingSec,
                                onTimerFinish = {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if (ejIndex == 0){
                                        onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                    //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                    stopChange(false)
                                    breakChange(false)
                                    //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                },
                                onTimerTick = timeRemainingSecChange,
                                inBreak = inBreak,
                                inStop = inStop,
                                stopChange = stopChange,
                                nextRemainingTime = timeCountdown
                            )
                        }
                    }


                }

            }

            Box(modifier = Modifier
                .padding(top=40.dp,bottom=40.dp)
            ){
                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                    color=(Color(0xFF000000)))
            }

            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ) {
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {

                                    if (rutina.cicles[cicleIndex].ejs[ejIndex].duration != 0) {
                                        stopChange(true)
                                        breakChange(true)
                                    } else {
                                        onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                        if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                    /*saracatunga*/

                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                            ) {
                                Text(
                                    text = stringResource(R.string.next),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {breakChange(!inBreak)},
                                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                            ) {
                                Text(
                                    text = stringResource(R.string.rest),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(text= stringResource(id = R.string.nextInCycle),
                                style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                            color= Color(0xFF000000))

                        Spacer(modifier = Modifier.height(10.dp))

                        rutina.cicles[cicleIndex].ejs.drop(ejIndex+1).forEach{ ejercicio ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .background(Color(0xFFBEBEBE))
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Box {
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${ejercicio.name}",
                                                style = MaterialTheme.typography.titleLarge
                                            )

                                            Text(
                                                text = "${ejercicio.description}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )

                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
