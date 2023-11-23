package hci.app.Composables

import android.content.ClipData.Item
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import hci.app.R
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.data.network.model.NetworkRoutineContent
import hci.app.data.network.model.NetworkRoutineCycles
import hci.app.ui.main.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun getRoutine(routineId : Int, viewModel: MainViewModel) : Map<Int, NetworkCycleExercises>{
    val routine = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    LaunchedEffect(key1 = routine){
        launch{
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                cycles?.content?.forEach { cycle ->
                    cycle.id?.let { cycleId ->
                        viewModel.getCycleExercises(cycleId).invokeOnCompletion {
                            val cycleExercises = viewModel.uiState.cycleExercises
                            cycleExercises?.let {
                                routine[cycleId] = it
                            }
                        }
                    }
                }
            }
        }
    }
    return routine
}

@Composable
fun getRoutineTotalCount(routineId : Int, viewModel: MainViewModel, cicleId : Int) : Int{
    val routine = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    var toReturn =0
    LaunchedEffect(key1 = routine){
        launch{
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                toReturn = cycles?.totalCount?:0
            }
        }
    }
    return toReturn
}


@Composable
fun MyRutineExecScreen1(viewModel: MainViewModel, routineId : Int) {

    val exercisesMap = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    LaunchedEffect(key1 = routineId, key2 = exercisesMap) {
        launch {
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                cycles?.content?.forEach { cycle ->
                    cycle.id?.let { cycleId ->
                        viewModel.getCycleExercises(cycleId).invokeOnCompletion {
                            val cycleExercises = viewModel.uiState.cycleExercises
                            cycleExercises?.let {
                                exercisesMap[cycleId] = it
                            }
                        }
                    }
                }
            }
        }
    }
    var routineData = viewModel.uiState.oneRoutine!!
    //mapa de id:ciclo --> ciclo<ejData>
    var rutina = getRoutine(routineId = routineId, viewModel = viewModel)

    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        //TabletRutineExec1Layout(viewModel, rutina, routineData,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    } else {
        PhoneRutineExec1Layout(viewModel, exercisesMap = exercisesMap)
    }
}
/*
@Composable
fun MyRutineExecScreen2(viewModel: MainViewModel,routineData: NetworkRoutineContent) {
    var routineId = routineData.id?:0
    var rutina = getRoutine(routineId = routineData.id?:0, viewModel = viewModel)

    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineExec2Layout(viewModel, rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    } else {
        PhoneRutineExec2Layout(viewModel, rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)},inBreak,{newValue -> viewModel.setInBreak(newValue)},timerRemainingSec,{newValue->viewModel.setTimerRemainingSec(newValue)},inStop,{newValue -> viewModel.setInStop(newValue)}, timeCountdown, {newValue -> viewModel.setTimeCountdown(newValue)})
    }
}
*/
@Composable
fun PhoneRutineExec1Layout(viewModel : MainViewModel, exercisesMap: MutableMap<Int, NetworkCycleExercises>) {
    var cycleId by remember{mutableStateOf(0)}
    var cycleName by remember{mutableStateOf("")}
    var cycleIndex by remember{mutableStateOf(0)}
    var totalCycleCount = viewModel.uiState.cycles?.totalCount?:0
    var totalExerciseCount = exercisesMap[viewModel.uiState.cycles?.content?.get(cycleIndex)?.id]?.totalCount?:0
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
                    Text(text="${viewModel.uiState.oneRoutine?.name}",
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
                        text = "${viewModel.uiState.cycles?.content?.get(cycleIndex)?.name}",
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
                        text = "${exercisesMap[viewModel.uiState.cycles?.content?.get(cycleIndex)?.id]?.content?.get()}",
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
                    val duration =rutina.get(cicleIndex)?.content?.get(ejIndex)?.duration?:0
                    val series = rutina.get(cicleIndex)?.content?.get(ejIndex)?.repetitions?:0 //rutina.cicles[cicleIndex].ejs[ejIndex].series


                    val displayText = if (duration == 0 || series == 0) {
                        if (duration == 0) {
                            "${series} series"
                        } else {
                            "${duration} m"
                        }
                    } else {
                        "${duration} m / ${series} series"      //todo chequear unidades
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
                    Text(       //rutina.cicles[cicleIndex].ejs[ejIndex].description
                        text = "${rutina.get(cicleIndex)?.content?.get(ejIndex)?.exercise?.detail?:""}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            item {if(!(timeRemainingSec>0))timeRemainingSecChange((rutina.get(cicleIndex)?.content?.get(ejIndex)?.duration?:0)*60)
                //TODO: esto se rompe porque no esta circularizado!!!

                changeTimeCountdown((rutina.get(cicleIndex)?.content?.get((ejIndex + 1) % totalCount)?.duration?:0)*60*1000)
                //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                //rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                ){
                    if(rutina.get(cicleIndex)?.content?.get(ejIndex)?.duration?:0!=0){
                        MyTimer(
                            seconds = timeRemainingSec,
                            onTimerFinish = {
                                if(cicleIndex>=totalRoutineCount && ejIndex>=totalCount){
                                    //todo navegacao
                                    return@MyTimer
                                }
                                onEjIndexChange((ejIndex + 1) % totalCount)
                                //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                if (ejIndex == 0){
                                    onCicleIndexChange((cicleIndex + 1) % totalRoutineCount)
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
                                if ((rutina.get(cicleIndex)?.content?.get(ejIndex)?.duration?:0) != 0) {
                                    //rutina.cicles[cicleIndex].ejs[ejIndex].duration
                                    stopChange(true)
                                    breakChange(true)
                                } else {
                                    onEjIndexChange((ejIndex + 1) % totalCount)   //rutina.cicles[cicleIndex].ejs.size
                                    if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % totalRoutineCount)
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

                    if ((rutina.get(cicleIndex)?.content?.get(ejIndex)?.duration?:0) != 0) {
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
/*
@Composable
fun TabletRutineExec1Layout(viewModel : MainViewModel, rutina: Map<Int, NetworkCycleExercises>, routineData:NetworkRoutineContent, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
    var cycleId by remember{mutableStateOf(0)}
    var cycleName by remember{mutableStateOf("")}
    LaunchedEffect(key1 = cycleName){
        launch{
            routineData.id?.let { viewModel.getOneCycle(it, cycleId) }
        }.invokeOnCompletion {
            cycleName = viewModel.uiState.oneCycle?.name ?:""   //nombre del ciclo actual
        }
    }
    var totalRoutineCount = getRoutineTotalCount(routineId = routineData.id?:0, viewModel = viewModel, cicleId = cicleIndex)
    var totalCount = rutina.get(cicleIndex)?.totalCount?:0

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
fun PhoneRutineExec2Layout(rutina: NetworkRoutineCycles?, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
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
fun TabletRutineExec2Layout(rutina: NetworkRoutineCycles?, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit) {
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
}*/
